package food_delivery_app_backend.Dto.ReviewEntity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEditDto {
    @NotNull
    private Long reviewId;
    private String review;
    @NotNull
    private Double rating;
}
