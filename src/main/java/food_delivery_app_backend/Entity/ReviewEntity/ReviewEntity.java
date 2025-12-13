package food_delivery_app_backend.Entity.ReviewEntity;

import food_delivery_app_backend.Dto.ReviewEntity.ReviewEditDto;
import food_delivery_app_backend.Entity.Business_Partner_Entity.FoodEntity;
import food_delivery_app_backend.Entity.UserEntities.GlobalUserEntity;
import food_delivery_app_backend.Entity.UserEntities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "food_review_entity",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "food_id"}))
@Builder
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review",nullable = false)
    private String review;
    @Column(name = "rating",nullable = false)
    private Double rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private FoodEntity food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private GlobalUserEntity user;

/*----------------------------------------------change values-----------------------------------------------*/

public void editReviews(ReviewEditDto dto){
    if (dto.getReview()!=null){
        this.setReview(dto.getReview());
    }
    if (dto.getRating()!=null){
        this.setRating(dto.getRating());
    }
}
}
