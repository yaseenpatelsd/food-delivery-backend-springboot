package food_delivery_app_backend.Mapping;

import food_delivery_app_backend.Dto.OrderDto.RazorpayResponseDto;
import food_delivery_app_backend.Entity.RazorPayEntity.RazorpayEntity;

public class RazorpayMapping {

    public static RazorpayResponseDto toResponseDto(RazorpayEntity entity){
        if (entity==null)return null;

        RazorpayResponseDto dto=new RazorpayResponseDto();
        dto.setAmount(entity.getAmount());
        dto.setCurrency(entity.getCurrency());
        dto.setStatus(entity.getStatus());
        dto.setRazorpayOrderId(entity.getRazorpayOrderId());
        dto.setRazorpayPaymentId(entity.getRazorpayPaymentId());

        return dto;
    }
}
