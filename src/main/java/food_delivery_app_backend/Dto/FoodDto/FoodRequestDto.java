package food_delivery_app_backend.Dto.FoodDto;

import food_delivery_app_backend.Enum.VegNonVeg;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Integer amount;
    @NotNull
    private VegNonVeg type;
    @NotNull
    private Boolean isAvailable;
    @NotBlank
    private String image;
    @NotBlank
    private String category;


}
