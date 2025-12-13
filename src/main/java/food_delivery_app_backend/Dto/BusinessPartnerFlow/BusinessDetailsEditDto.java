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
public class BusinessDetailsEditDto {
    @NotNull
    private Long businessId;
    @NotBlank
    private String username;
    private String name;
    private BusinessType businessType;
    private String address;
    private String city;
    private Boolean isActive;
    private String state;
    private String country;
    private BusinessStatus status;
    private String cuisineType;
    private String fssaiNumber;
    private VegNonVeg vegType;
}
