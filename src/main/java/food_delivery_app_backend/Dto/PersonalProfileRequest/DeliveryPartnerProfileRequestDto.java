package food_delivery_app_backend.Dto.PersonalProfileRequest;

import food_delivery_app_backend.Dto.PersonalProfileRequest.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPartnerProfileRequestDto extends GlobalRequestDto {
    @NotBlank
    private String licenceNo;
}
