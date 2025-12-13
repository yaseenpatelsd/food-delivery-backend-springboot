package food_delivery_app_backend.Dto.DeliveryPartnerFlow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryConfirmDto {
    private Long orderId;
    private String otp;
}
