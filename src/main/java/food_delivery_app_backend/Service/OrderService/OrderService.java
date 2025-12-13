package food_delivery_app_backend.Service.OrderService;

import com.razorpay.Order;
import com.razorpay.RazorpayException;
import food_delivery_app_backend.Config.RazorPayConfig;
import food_delivery_app_backend.Dto.DeliveryPartnerFlow.DPGetOrderDto;
import food_delivery_app_backend.Dto.DeliveryPartnerFlow.DeliveryConfirmDto;
import food_delivery_app_backend.Dto.DeliveryPartnerFlow.DeliveryPartnerGetOrderDto;
import food_delivery_app_backend.Dto.OrderDto.*;
import food_delivery_app_backend.Entity.Business_Partner_Entity.BusinessPartnerEntity;
import food_delivery_app_backend.Entity.Business_Partner_Entity.FoodEntity;
import food_delivery_app_backend.Entity.Order.OrderEntity;
import food_delivery_app_backend.Entity.Order.OrderItemEntity;
import food_delivery_app_backend.Entity.PersonalDetailEntity.UserPersonalProfileEntity;
import food_delivery_app_backend.Entity.RazorPayEntity.RazorpayEntity;
import food_delivery_app_backend.Entity.UserEntities.DeliveryPartnerEntity;
import food_delivery_app_backend.Entity.UserEntities.OwnerEntity;
import food_delivery_app_backend.Entity.UserEntities.UserEntity;
import food_delivery_app_backend.Enum.OrderStatus;
import food_delivery_app_backend.Enum.PaymentStatus;
import food_delivery_app_backend.Exeption.IllegalArgumentException;
import food_delivery_app_backend.Exeption.ResouceNotFoundException;
import food_delivery_app_backend.Exeption.SomethingIsWrongException;
import food_delivery_app_backend.Exeption.UserNotFoundException;
import food_delivery_app_backend.Mapping.RazorpayMapping;
import food_delivery_app_backend.Repository.FoodRepository;
import food_delivery_app_backend.Repository.Global.DeliveryPartnerRepository;
import food_delivery_app_backend.Repository.Global.OwnerRepository;
import food_delivery_app_backend.Repository.Global.UserRepository;
import food_delivery_app_backend.Repository.OrderItemEntityRepository;
import food_delivery_app_backend.Repository.OrderRepository;
import food_delivery_app_backend.Repository.PersonalDetail.UserPersonalDetailsRepository;
import food_delivery_app_backend.Repository.RazorPayRepository;
import food_delivery_app_backend.Service.EmailService;
import food_delivery_app_backend.Util.OrderNumberGenerator;
import food_delivery_app_backend.Util.OtpGeneration;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.awt.geom.Point2D.distance;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final RazorPayConfig razorpayClient;
    private final OrderNumberGenerator orderNumberGenerator;
    private final UserPersonalDetailsRepository userPersonalDetailsRepository;
    private final OtpGeneration otpGeneration;
    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final RazorPayRepository razorPayRepository;
    private final OwnerRepository ownerRepository;
    private final OrderItemEntityRepository orderItemEntityRepository;
    private final EmailService emailService;

    public OrderService(
            OrderRepository orderRepository,
            FoodRepository foodRepository,
            UserRepository userRepository,
            RazorPayConfig razorpayClient,
            OrderNumberGenerator orderNumberGenerator,
            UserPersonalDetailsRepository userPersonalDetailsRepository,
            OtpGeneration otpGeneration,
            DeliveryPartnerRepository deliveryPartnerRepository,
            RazorPayRepository razorPayRepository,
            OwnerRepository ownerRepository, OrderItemEntityRepository orderItemEntityRepository, EmailService emailService) {

        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
        this.razorpayClient = razorpayClient;
        this.orderNumberGenerator = orderNumberGenerator;
        this.userPersonalDetailsRepository = userPersonalDetailsRepository;
        this.otpGeneration = otpGeneration;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
        this.razorPayRepository = razorPayRepository;
        this.ownerRepository = ownerRepository;
        this.orderItemEntityRepository = orderItemEntityRepository;
        this.emailService = emailService;
    }

    /* -----------------------------------------------------------
       CREATE ORDER + RAZORPAY ORDER
    ------------------------------------------------------------ */

    public RazorpayResponseDto razorpayResponseDto(Authentication authentication, OrderRequestDto dto)
            throws RazorpayException {

        UserEntity user = userEntity(authentication);

        List<Long> requestedFoodIds = dto.getItems()
                .stream()
                .map(CartItemDto::getFoodId)
                .collect(Collectors.toList());

        List<FoodEntity> foodEntities = foodRepository.findAllById(requestedFoodIds);

        if (foodEntities.size() != requestedFoodIds.size()) {
            throw new ResouceNotFoundException("Some foods not found. Requested: " + requestedFoodIds);
        }

        if (foodEntities.stream().anyMatch(f -> !f.getIsAvailable())) {
            throw new SomethingIsWrongException("Food not available anymore");
        }

        UserPersonalProfileEntity profile = personalProfile(user);

        double totalAmount = dto.getItems().stream()
                .mapToDouble(item -> {
                    FoodEntity food = foodEntities.stream()
                            .filter(f -> f.getId().equals(item.getFoodId()))
                            .findFirst()
                            .orElseThrow(() -> new ResouceNotFoundException("Food not found: " + item.getFoodId()));
                    return food.getAmount() * item.getQuantity();
                }).sum();

        double discount = totalAmount * 0.20;
        double deliveryCharge = 80.0;
        double tax = (totalAmount + deliveryCharge) * (totalAmount > 7500 ? 0.18 : 0.05);
        double finalAmount = totalAmount + deliveryCharge + tax - discount;

        Long razorpayAmount = (long) (finalAmount*100);


        // Razorpay Order JSON
        JSONObject json = new JSONObject();
        json.put("amount", razorpayAmount);
        json.put("currency", "INR");
        json.put("receipt", "txn_" + System.currentTimeMillis());
        json.put("payment_capture", 1);

        Order rpOrder = razorpayClient.razorpayClient().orders.create(json);

        RazorpayEntity razorpay = RazorpayEntity.builder()
                .amount(razorpayAmount)
                .currency("INR")
                .status(rpOrder.get("status"))
                .razorpayOrderId(orderNumberGenerator.generateOrderNumber())
                .razorpayPaymentId("Test-Payment-" + System.currentTimeMillis())
                .food(foodEntities)
                .user(user)
                .build();

        razorPayRepository.save(razorpay);

        BusinessPartnerEntity partner = foodEntities.get(0).getBusinessPartner();
        long restaurantCount = foodEntities.stream()
                .map(f -> f.getBusinessPartner().getId())
                .distinct()
                .count();

        if (restaurantCount > 1) {
            throw new SomethingIsWrongException("Multiple restaurant items not allowed");
        }

        // Build order entity
        OrderEntity orderEntity = OrderEntity.builder()
                .orderNumber(orderNumberGenerator.generateOrderNumber())
                .razorpayOrderId(razorpay.getRazorpayOrderId())
                .razorpayPaymentId(null)
                .paymentStatus(PaymentStatus.Pending)
                .totalAmount(finalAmount*100)
                .taxAmount(tax)
                .deliveryFee(80)
                .discount("20% flat off")
                .finalAmount(finalAmount)
                .addressLine(profile.getAddress())
                .city(profile.getCity())
                .state(profile.getState())
                .country(profile.getCountry())
                .pinCode(profile.getPinCode())
                .user(user)
                .restaurant(partner)
                .orderStatus(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        // Create order items safely
        List<OrderItemEntity> items = dto.getItems().stream()
                .map(ci -> {
                    FoodEntity food = foodEntities.stream()
                            .filter(f -> f.getId().equals(ci.getFoodId()))
                            .findFirst()
                            .orElseThrow(() -> new ResouceNotFoundException("Invalid food in cart"));
                    return OrderItemEntity.builder()
                            .food(food)
                            .quantity(ci.getQuantity())
                            .order(orderEntity)
                            .build();
                }).toList();

        items.forEach(orderEntity::addItem);
        orderRepository.save(orderEntity);


        Long frontendAmount= Math.round(finalAmount);

        return new RazorpayResponseDto(
                frontendAmount,
                razorpay.getCurrency(),
                razorpay.getStatus(),
                razorpay.getRazorpayPaymentId(),
                razorpay.getRazorpayOrderId());
    }

    /* -----------------------------------------------------------
       RESTAURANT ACCEPTS ORDER
    ------------------------------------------------------------ */

    public void businessAcceptOrder(Authentication authentication,Long id) {
        OrderEntity order = findOrderEntity(id);
        orderShouldNotBeCancel(order);

        if (!order.getRestaurant().getOwner().getUsername().equals(authentication.getName())){
            throw new SomethingIsWrongException("NOT ALLOWED");
        }

        order.setOrderStatus(OrderStatus.ACCEPTED_BY_RESTAURANT);

        // Set OTP
        order.setBusinessOtp(otpGeneration.generateOtp());
        order.setBusinessOtpExpire(otpGeneration.otpExpireDate(10));

        order.setUserDeliveryConfirmOtp(otpGeneration.generateOtp());
        order.setUserDeliveryConfirmOtpExpire(otpGeneration.otpExpireDate(30));



        orderRepository.save(order);
    }

    /* -----------------------------------------------------------
       OWNER CONFIRMS PAYMENT
    ------------------------------------------------------------ */

    public void ConfirmingPaymentByOwner(Authentication auth, Long orderId) {
        OrderEntity order = findOrderEntity(orderId);
        OwnerEntity owner = findOwner(auth);

        if (!order.getRestaurant().getOwner().getId().equals(owner.getId())) {
            throw new IllegalArgumentException("Not allowed");
        }

        order.setPaymentStatus(PaymentStatus.Success);
        orderRepository.save(order);
    }

    /* -----------------------------------------------------------
       USER CANCEL ORDER
    ------------------------------------------------------------ */

    public void cancelOrder(Authentication auth, Long orderId) {
        UserEntity user = userEntity(auth);
        OrderEntity order = findOrderEntity(orderId);

        if (!user.getId().equals(order.getUser().getId())) {
            throw new IllegalArgumentException("Not allowed to cancel this order");
        }

        order.setOrderStatus(OrderStatus.CANCEL);

        if (order.getDeliveryPartner() != null) {
            order.getDeliveryPartner().setOccupied(false);
            deliveryPartnerRepository.save(order.getDeliveryPartner());
        }

        orderRepository.save(order);
    }

    /* -----------------------------------------------------------
       RESTAURANT REJECT ORDER
    ------------------------------------------------------------ */

    public void OrderReject(Authentication authentication,Long id) {
        OrderEntity order = findOrderEntity(id);

        if (!order.getRestaurant().getOwner().getUsername().equals(authentication.getName())) {
            throw new SomethingIsWrongException("Not allowed to do this task");
        }

        order.setOrderStatus(OrderStatus.REJECTED_BY_RESTAURANT);

        if (order.getPaymentStatus() == PaymentStatus.Success) {
            order.setPaymentStatus(PaymentStatus.Refunded);
        }

        orderRepository.save(order);
    }

    /* -----------------------------------------------------------
     OrderAccept And delivery boy assign
    --------------------------------------------------------------*/

    public void assignDeliveryPartnerManually(Long orderId, String username) {

        OrderEntity order = findOrderEntity(orderId);
        DeliveryPartnerEntity dp = deliveryPartnerRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Driver not found"));

        if (dp.getOnDuty().equals(false)) {
            throw new SomethingIsWrongException("Delivery boy is not available");
        }

        if (dp.getOccupied()) {
            throw new SomethingIsWrongException("Delivery boy is Busy with another order");
        }

        order.setDeliveryPartner(dp);
        dp.setOccupied(true);

        // Generate OTPs
        order.setBusinessOtp(otpGeneration.generateOtp());
        order.setBusinessOtpExpire(otpGeneration.otpExpireDate(10));

        String otp=order.getBusinessOtp();

        emailService.emailToConfirmPickUp(dp.getEmail(),otp, dp, order);

        order.setUserDeliveryConfirmOtp(otpGeneration.generateOtp());
        order.setUserDeliveryConfirmOtpExpire(otpGeneration.otpExpireDate(30));

        String userOtp=order.getUserDeliveryConfirmOtp();

        UserEntity user=order.getUser();
        emailService.deliveryConfrimOtp(order.getUser().getEmail(),userOtp, user, dp);

        deliveryPartnerRepository.save(dp);
        orderRepository.save(order);
    }


    /* -----------------------------------------------------------
       DRIVER PICKUP
    ------------------------------------------------------------ */

    public void orderPickUpByDeliveryPartner(Authentication authentication,DeliveryPartnerGetOrderDto dto) {
        OrderEntity order = findOrderEntity(dto.getOrderId());
        DeliveryPartnerEntity dp = findDeliveryPartner(authentication);

        if (!dp.getUsername().equals(order.getDeliveryPartner().getUsername())){
            throw new SomethingIsWrongException("You are not Authorized to pick up this order");
        }

        if (order.getDeliveryPartner() == null ||
                !order.getDeliveryPartner().getId().equals(dp.getId())) {

            throw new SomethingIsWrongException("You are not assigned to this order");
        }

        String otp = order.getBusinessOtp();
        if (otp == null || !otp.equals(dto.getOtp())) {
            throw new SomethingIsWrongException("Invalid OTP");
        }

        if (order.getBusinessOtpExpire() < System.currentTimeMillis()) {
            throw new SomethingIsWrongException("OTP expired");
        }

        order.setOrderStatus(OrderStatus.PICKED_UP);
        order.setBusinessOtp(null);
        order.setBusinessOtpExpire(null);
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);
    }

    /* -----------------------------------------------------------
       DELIVER ORDER TO USER
    ------------------------------------------------------------ */

    @Transactional
    public void deliveredToUser(DeliveryConfirmDto dto) {
        OrderEntity order = findOrderEntity(dto.getOrderId());
        orderShouldNotBeCancel(order);

        String userOtp = order.getUserDeliveryConfirmOtp();
        if (userOtp == null || !userOtp.equals(dto.getOtp())) {
            throw new SomethingIsWrongException("Invalid OTP");
        }

        if (order.getUserDeliveryConfirmOtpExpire() < System.currentTimeMillis()) {
            throw new SomethingIsWrongException("Delivery OTP expired");
        }

        DeliveryPartnerEntity dp = order.getDeliveryPartner();
        if (dp != null) {
            dp.setOccupied(false);
            deliveryPartnerRepository.save(dp);
        }

        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setDeliveredAt(LocalDateTime.now());
        order.setUserDeliveryConfirmOtp(null);

        orderRepository.save(order);
    }



    /*-------------------------------------------------------------------------
    user order list
    ---------------------------------------------------------------------------*/
    public List<OrderUserListResponseDto> userListResponseDto(Authentication authentication) {

        UserEntity user = userEntity(authentication);

        List<OrderEntity> orders = orderRepository.findByUser_Username(user.getUsername());


        return orders.stream().map(orderEntity -> {

            int totalQty = orderEntity.getItems()
                    .stream()
                    .mapToInt(item -> item.getQuantity() == null ? 0 : item.getQuantity())
                    .sum();

            // Foods list
            List<FoodResponseDto> foodDtos = orderEntity.getItems().stream()
                    .map(item -> {
                        FoodResponseDto f = new FoodResponseDto();
                        f.setName(item.getFood().getName());
                        f.setDescription(item.getFood().getDescription());
                        f.setAmount(item.getFood().getAmount());
                        f.setQuantity(totalQty);
                        f.setType(item.getFood().getType());
                        f.setImage(item.getFood().getImage());
                        f.setCategory(item.getFood().getCategory());
                        return f;
                    })
                    .collect(Collectors.toList());

            // Order DTO
            OrderUserListResponseDto dto = new OrderUserListResponseDto();
            dto.setFoods(foodDtos);

            dto.setTotalAmount(orderEntity.getTotalAmount() / 100);


            dto.setStatus(orderEntity.getOrderStatus());
            dto.setOrderId(orderEntity.getOrderNumber());
            dto.setRazorpayOrderId(orderEntity.getRazorpayOrderId());
            dto.setRazorpayPaymentId(orderEntity.getRazorpayPaymentId());

            return dto;

        }).collect(Collectors.toList());
    }

    /*---------------------------------------------------------------
    delivery boy deny order
    */

    @Transactional
    public String deliveryBoyQuite(Authentication authentication,Long orderNo){
        DeliveryPartnerEntity dp=findDeliveryPartner(authentication);
        OrderEntity order=findOrderEntity(orderNo);

        dp.setOccupied(false);


        if (!order.getOrderStatus().equals(OrderStatus.DRIVER_ASSIGNED)) {
            throw new SomethingIsWrongException("You either dont have order or you already pick up the order");
        }

        if (!dp.getOccupied().equals(true)) {
            throw new SomethingIsWrongException("you dont have any order to cancel");
        }

        dp.setOccupied(false);


        order.setOrderStatus(OrderStatus.FINDING_DELIVERY_PARTNER);
        order.setDeliveryPartner(null);

        emailService.deliveryPartnerCencelOrder(order.getRestaurant().getOwner().getEmail(), order);

        orderRepository.save(order);
        deliveryPartnerRepository.save(dp);

        return "Order Cancel Successfully";
    }


    public String deleteMe(Authentication authentication){
        DeliveryPartnerEntity dp=findDeliveryPartner(authentication);

        dp.setOccupied(false);

        deliveryPartnerRepository.save(dp);

        return "Sucessful now delete this";
    }

    /* -----------------------------------------------------------
       HELPERS
    ------------------------------------------------------------ */

    public UserEntity userEntity(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public UserPersonalProfileEntity personalProfile(UserEntity user) {
        return userPersonalDetailsRepository.findByUser(user)
                .orElseThrow(() -> new ResouceNotFoundException("Missing personal info"));
    }

    public OrderEntity findOrderEntity(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResouceNotFoundException("Order not found"));
    }

    public DeliveryPartnerEntity findDeliveryPartner(Authentication authentication) {
        return deliveryPartnerRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("Delivery partner not found"));
    }

    public OwnerEntity findOwner(Authentication auth) {
        return ownerRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UserNotFoundException("Owner account not found"));
    }

    public void orderShouldNotBeCancel(OrderEntity order) {
        if (order.getOrderStatus() == OrderStatus.CANCEL) {
            throw new SomethingIsWrongException("Order already cancelled");
        }
    }
}

