package food_delivery_app_backend.Dto.GlobalResponse;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalResponseDto {

    private String username;
    private String password;
    private String email;

}
