package food_delivery_app_backend.Mapping;

import food_delivery_app_backend.Dto.ReviewEntity.ReviewRequstDto;
import food_delivery_app_backend.Entity.ReviewEntity.ReviewEntity;

public class ReviewMapping {

    public static ReviewRequstDto toDto(ReviewEntity entity){
        if (entity==null)return  null;

        ReviewRequstDto dto=new ReviewRequstDto();
        dto.setReview(entity.getReview());
        dto.setRating(entity.getRating());

        return dto;
    }

    public static ReviewEntity entity(ReviewRequstDto dto){
        if (dto==null)return null;

        return ReviewEntity.builder()
                .review(dto.getReview())
                .rating(dto.getRating())
                .build();
    }
}
