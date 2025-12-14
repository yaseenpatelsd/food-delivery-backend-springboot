package food_delivery_app_backend.Dto.PersonalProfileRequest;

import food_delivery_app_backend.Enum.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalRequestDto {
    @NotBlank
    private String fullName;
    @NotNull
    private Gender gender;
    @NotBlank
    private String address;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String country;
    @NotNull
    private Long pincode;
    @NotBlank
    private String mobileNumber;

}
