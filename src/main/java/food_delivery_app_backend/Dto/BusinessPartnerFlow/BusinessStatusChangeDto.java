package food_delivery_app_backend.Dto.BusinessPartnerFlow;

import food_delivery_app_backend.Enum.BusinessStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessStatusChangeDto {
   @NotNull
   private Long businessId;
   @NotBlank
   private String username;
   @NotNull
    private BusinessStatus status;

}
