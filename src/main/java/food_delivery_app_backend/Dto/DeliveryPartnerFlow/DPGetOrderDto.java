package food_delivery_app_backend.Dto.DeliveryPartnerFlow;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DPGetOrderDto {
    @NotNull
        private Long orderId;
    @NotNull
        private Long deliveryPartnerId;
    }
