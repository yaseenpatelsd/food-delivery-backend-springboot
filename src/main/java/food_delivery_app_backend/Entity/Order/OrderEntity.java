package food_delivery_app_backend.Entity.Order;

import food_delivery_app_backend.Entity.Business_Partner_Entity.BusinessPartnerEntity;
import food_delivery_app_backend.Entity.UserEntities.DeliveryPartnerEntity;
import food_delivery_app_backend.Entity.UserEntities.UserEntity;
import food_delivery_app_backend.Enum.OrderStatus;
import food_delivery_app_backend.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
@Builder
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    // Payment
    private String razorpayOrderId;
    private String razorpayPaymentId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    // Order status
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // Amounts
    private double totalAmount;
    private double taxAmount;
    private double deliveryFee;
    private String discount;
    private double finalAmount;

    // Delivery address
    private String addressLine;
    private String city;
    private String state;
    private String country;
    private Long pinCode;

    // Time tracking
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deliveredAt;

    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_partner_id")
    private DeliveryPartnerEntity deliveryPartner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private BusinessPartnerEntity restaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> items = new ArrayList<>();


    //otp for verification;
    private String businessOtp;
    private Long businessOtpExpire;
    private String UserDeliveryConfirmOtp;
    private Long userDeliveryConfirmOtpExpire;



    public void addItem(OrderItemEntity item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
        item.setOrder(this);
    }



}
