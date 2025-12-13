package food_delivery_app_backend.Dto.BusinessPartnerFlow;

import food_delivery_app_backend.Enum.BusinessStatus;
import food_delivery_app_backend.Enum.BusinessType;
import food_delivery_app_backend.Enum.VegNonVeg;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessPartnerRequestDto {
    @NotBlank
    private String name;
    @NotNull
    private BusinessType businessType;
    @NotBlank
    private String address;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String country;
    @NotNull
    private Boolean isActive;
    @NotNull
    private BusinessStatus status;
    @NotBlank
    private String cuisineType;
    @NotBlank
    private String fssaiNumber;
    @NotNull
    private VegNonVeg vegType;

}
