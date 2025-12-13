package food_delivery_app_backend.Dto.GlobalResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerResponseDto extends GlobalResponseDto{
    private String vehicleNo;
    private Boolean onDuty;
}
