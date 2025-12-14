package food_delivery_app_backend.Dto.PersonalProfileResponse;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPartnerProfileResponseDto extends GlobalResponseDto {
    @NotBlank
    private String licenceNo;
}
