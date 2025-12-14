package food_delivery_app_backend.Dto.UserRegisterFlow;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountVerificationDto {
    @NotBlank
    private String username;
    @NotBlank
    private String otp;
}
