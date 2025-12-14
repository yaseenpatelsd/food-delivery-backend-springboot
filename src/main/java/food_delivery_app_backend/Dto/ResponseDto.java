package food_delivery_app_backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private LocalDateTime localDateTime;
    private int status;
    private String error;
    private String message;
    private String path;
}
