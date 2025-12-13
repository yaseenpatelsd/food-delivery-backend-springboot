package food_delivery_app_backend.Dto.DeliveryPartnerFlow;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerGetOrderDto {
    @NotNull
    private Long orderId;
    @NotBlank
    private String otp;
}
