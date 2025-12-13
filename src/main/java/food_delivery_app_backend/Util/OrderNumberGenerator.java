package food_delivery_app_backend.Util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class OrderNumberGenerator {

    public String generateOrderNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "ORD-" + LocalDateTime.now().format(formatter);
    }
}
