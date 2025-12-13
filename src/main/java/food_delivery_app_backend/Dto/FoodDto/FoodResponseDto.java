package food_delivery_app_backend.Dto.FoodDto;

import food_delivery_app_backend.Entity.Business_Partner_Entity.BusinessPartnerEntity;
import food_delivery_app_backend.Enum.VegNonVeg;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodResponseDto {
    private String name;
    private String description;
    private Integer amount;
    private VegNonVeg type;
    private Boolean isAvailable;
    private String image;
    private String category;
    private String businessPartnerName;
    private String businessPartnerAddress;
    private Double averageRating;

}
