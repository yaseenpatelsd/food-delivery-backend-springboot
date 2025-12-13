package food_delivery_app_backend.Entity.RazorPayEntity;

import food_delivery_app_backend.Entity.Business_Partner_Entity.FoodEntity;
import food_delivery_app_backend.Entity.UserEntities.GlobalUserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "razorpay_entity")
@Builder
public class RazorpayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long amount;
    private String currency;
    private String status;


    private String razorpayOrderId;
    private String razorpayPaymentId;

    @ManyToMany
    @JoinTable(
            name = "razorpay_food",
            joinColumns = @JoinColumn(name = "razorpay_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private List<FoodEntity> food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private GlobalUserEntity user;
}
