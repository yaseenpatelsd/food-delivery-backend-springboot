package food_delivery_app_backend.Service.ReviewService;

import food_delivery_app_backend.Dto.ReviewEntity.ReviewEditDto;
import food_delivery_app_backend.Dto.ReviewEntity.ReviewRequstDto;
import food_delivery_app_backend.Dto.ReviewEntity.ReviewResposeDto;
import food_delivery_app_backend.Entity.Business_Partner_Entity.FoodEntity;
import food_delivery_app_backend.Entity.ReviewEntity.ReviewEntity;
import food_delivery_app_backend.Entity.UserEntities.GlobalUserEntity;
import food_delivery_app_backend.Entity.UserEntities.UserEntity;
import food_delivery_app_backend.Exeption.IllegalArgumentException;
import food_delivery_app_backend.Exeption.ResouceNotFoundException;
import food_delivery_app_backend.Exeption.UserNotFoundException;
import food_delivery_app_backend.Mapping.ReviewMapping;
import food_delivery_app_backend.Repository.FoodRepository;
import food_delivery_app_backend.Repository.Global.GlobalUserRepository;
import food_delivery_app_backend.Repository.Global.UserRepository;
import food_delivery_app_backend.Repository.ReviewRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, FoodRepository foodRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.foodRepository = foodRepository;
        this.userRepository =userRepository ;
    }

    public ReviewResposeDto giveReview(Authentication authentication, ReviewRequstDto dto) {
        UserEntity user=findUser(authentication);
        FoodEntity food=foodEntity(dto.getFoodId());


        ReviewEntity reviewEntity=ReviewMapping.entity(dto);

        reviewEntity.setFood(food);
        reviewEntity.setUser(user);

        updateAverageRating(food);
        reviewRepository.save(reviewEntity);

        ReviewRequstDto reviewRequstDto=ReviewMapping.toDto(reviewEntity);;

        return ReviewResposeDto.builder()
                .review(reviewRequstDto.getReview())
                .rating(reviewRequstDto.getRating())
                .build();
    }

    public ReviewResposeDto getReview(Authentication authentication, Long id){
        ReviewEntity reviewEntity=reviewEntity(id);

        FoodEntity food=reviewEntity.getFood();

        updateAverageRating(food);
        foodRepository.save(food);

        ReviewRequstDto reviewRequstDto=ReviewMapping.toDto(reviewEntity);;

        return ReviewResposeDto.builder()
                .review(reviewRequstDto.getReview())
                .rating(reviewRequstDto.getRating())
                .build();

    }
    public ReviewResposeDto editReview(Authentication authentication, ReviewEditDto dto){
        ReviewEntity reviewEntity=reviewEntity(dto.getReviewId());
        FoodEntity foodEntity=foodEntity(reviewEntity.getFood().getId());

        confirmUser(authentication,reviewEntity);
        reviewEntity.editReviews(dto);



        reviewRepository.save(reviewEntity);

        updateAverageRating(foodEntity);

        ReviewRequstDto reviewRequstDto=ReviewMapping.toDto(reviewEntity);;

        return ReviewResposeDto.builder()
                .rating(reviewRequstDto.getRating())
                .review(reviewRequstDto.getReview())
                .build();
    }


    public void deleteReview(Authentication authentication,Long reviewId){
        ReviewEntity reviewEntity=reviewEntity(reviewId);

        FoodEntity foodEntity=foodEntity(reviewEntity.getFood().getId());

        confirmUser(authentication,reviewEntity);

        reviewRepository.delete(reviewEntity);
        updateAverageRating(foodEntity(foodEntity.getId()));

    }

    private void updateAverageRating(FoodEntity food) {

        List<ReviewEntity> reviews = reviewRepository.findByFood(food);

        double average = reviews.stream()
                .mapToDouble(ReviewEntity::getRating)
                .average()
                .orElse(0);

        // Store double, NOT int
        food.setAverageRating( average);

        foodRepository.save(food);
    }


    public double getAverageRating(Long id){
    FoodEntity foodEntity=foodEntity(id);
        return foodEntity.getAverageRating();
    }

    public List<ReviewRequstDto> findAllReviewOfFood(Long id){
        FoodEntity foodEntity=foodEntity(id);

        List<ReviewEntity> review=reviewRepository.findByFood(foodEntity);
        return review.stream().map(ReviewMapping::toDto).collect(Collectors.toList());
    }

/*---------------------------------------------helper methods----------------------------------------------*/

    public ReviewEntity reviewEntity(Long id){
    return reviewRepository.findById(id)
            .orElseThrow(()-> new ResouceNotFoundException("Can't find a review by the id"));
   }

   public FoodEntity foodEntity(Long id){
        return foodRepository.findById(id)
                .orElseThrow(()-> new ResouceNotFoundException("Can' find the food with the id"));
   }

   public UserEntity findUser(Authentication authentication){
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UserNotFoundException("Can't find any account with provided username"));
   }

   public void confirmUser(Authentication authentication,ReviewEntity reviewEntity){
       if (!reviewEntity.getUser().getUsername().equals(authentication.getName())){
           throw new IllegalArgumentException("You are not the owner of this review.");
       }

   }

}
