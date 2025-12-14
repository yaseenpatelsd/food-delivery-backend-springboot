package food_delivery_app_backend.Dto.ReviewEntity;

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
public class ReviewResposeDto {
    @NotBlank
    private String review;
    @NotNull
    private Double rating;
}
