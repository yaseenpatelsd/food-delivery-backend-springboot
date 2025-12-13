package food_delivery_app_backend.Controller;

import com.razorpay.RazorpayException;
import food_delivery_app_backend.Dto.DeliveryPartnerFlow.DeliveryConfirmDto;
import food_delivery_app_backend.Dto.DeliveryPartnerFlow.DeliveryPartnerGetOrderDto;
import food_delivery_app_backend.Dto.OrderDto.OrderRequestDto;
import food_delivery_app_backend.Dto.OrderDto.OrderUserListResponseDto;
import food_delivery_app_backend.Dto.OrderDto.RazorpayResponseDto;
import food_delivery_app_backend.Service.OrderService.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<RazorpayResponseDto> buy(Authentication authentication, @RequestBody OrderRequestDto dto) throws RazorpayException {
      RazorpayResponseDto razorpayResponseDto=orderService.razorpayResponseDto(authentication,dto);
      return ResponseEntity.ok(razorpayResponseDto);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/owner/accept/{id}")
    public ResponseEntity<String> acceptOrder(@PathVariable Long id,@RequestParam String username){
        orderService.assignDeliveryPartnerManually(id,username);
        return ResponseEntity.ok("Order accepted");
    }
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/owner/cancel/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id, Authentication authentication){
        orderService.OrderReject(authentication,id);
        return ResponseEntity.ok("Cancel order");
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/user/cancelation/{id}")
    public ResponseEntity<String> userCancelOrder(Authentication authentication,@PathVariable Long id){
        orderService.cancelOrder(authentication,id);
        return ResponseEntity.ok("Order cancel sucessfully");
    }
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("owner/payment/confirm/{id}")
    public ResponseEntity<String> ownerConfirmPayment(Authentication authentication,@PathVariable Long id){
        orderService.ConfirmingPaymentByOwner(authentication,id);
        return ResponseEntity.ok("Payment is  confirm by owner");
    }

    @PreAuthorize("hasRole('DELIVERY_PARTNER')")
    @PostMapping("/pick/up")
    public ResponseEntity<String> deliveryPartnerPickUp(Authentication authentication,@RequestBody DeliveryPartnerGetOrderDto dto){
        orderService.orderPickUpByDeliveryPartner(authentication,dto);
        return ResponseEntity.ok("Order is pick up");
    }
    @PreAuthorize("hasRole('DELIVERY_PARTNER')")
    @PostMapping("/deliver/confirm")
    public ResponseEntity<String> DeliveryConfirm(@RequestBody DeliveryConfirmDto dto){
        orderService.deliveredToUser(dto);
        return ResponseEntity.ok("Delivery order successful ");
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<OrderUserListResponseDto>> DeliveryConfirm(Authentication authentication) {

        List<OrderUserListResponseDto> orderUserListResponseDtos =
                orderService.userListResponseDto(authentication);

        return ResponseEntity.ok(orderUserListResponseDtos);
    }

    @PreAuthorize("hasRole('DELIVERY_PARTNER')")
    @PatchMapping("/delivery/partner/order-cancel/{id}")
    public ResponseEntity<String> deliveryBoyCancel(Authentication authentication,@PathVariable Long id){
        orderService.deliveryBoyQuite(authentication,id);
        return ResponseEntity.ok("order cancel successfully");
    }


    @PatchMapping("/delivery/partner/free/me")
    public ResponseEntity<String> deleteAfterOneTimeUse(Authentication authentication){
        orderService.deleteMe(authentication);
        return ResponseEntity.ok("order cancel successfully");
    }


}
