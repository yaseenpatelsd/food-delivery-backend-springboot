package food_delivery_app_backend.Dto.DeliveryPartnerFlow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPartnerLoginDto {
    private String username;
    private String password;
}
