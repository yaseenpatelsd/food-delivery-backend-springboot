package food_delivery_app_backend.Mapping;

import food_delivery_app_backend.Dto.FoodDto.FoodRequestDto;
import food_delivery_app_backend.Dto.FoodDto.FoodResponseDto;
import food_delivery_app_backend.Entity.Business_Partner_Entity.FoodEntity;

public class FoodMapping {

    public static FoodEntity toEntity(FoodRequestDto dto){
        if (dto==null) return null;

        return FoodEntity.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .type(dto.getType())
                .isAvailable(dto.getIsAvailable())
                .image(dto.getImage())
                .category(dto.getCategory())
                .build();
    }

    public static FoodRequestDto toDto(FoodEntity entity){
        if (entity==null)return null;

        FoodRequestDto dto=new FoodRequestDto();
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setAmount(entity.getAmount());
        dto.setType(entity.getType());
        dto.setIsAvailable(entity.getIsAvailable());
        dto.setImage(entity.getImage());
        dto.setCategory(entity.getCategory());
        return dto;
    }

    public static FoodResponseDto toResponseDto(FoodEntity entity){
        if (entity==null)return null;

        FoodResponseDto dto=new FoodResponseDto();
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setAmount(entity.getAmount());
        dto.setType(entity.getType());
        dto.setIsAvailable(entity.getIsAvailable());
        dto.setImage(entity.getImage());
        dto.setCategory(entity.getCategory());
        dto.setBusinessPartnerName(entity.getBusinessPartner().getName());
        dto.setBusinessPartnerAddress(entity.getBusinessPartner().getAddress());
        dto.setAverageRating(entity.getAverageRating());

        return dto;
    }
}
