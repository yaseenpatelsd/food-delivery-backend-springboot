package food_delivery_app_backend.Dto.FoodDto;

import food_delivery_app_backend.Enum.VegNonVeg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodEditDto {
    private String name;
    private String description;
    private Integer amount;
    private VegNonVeg type;
    private Boolean isAvailable;
    private String image;
    private String category;
    private Integer averageRating;
}
