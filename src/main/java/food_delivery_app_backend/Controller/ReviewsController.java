package food_delivery_app_backend.Controller;

import food_delivery_app_backend.Dto.ReviewEntity.ReviewEditDto;
import food_delivery_app_backend.Dto.ReviewEntity.ReviewRequstDto;
import food_delivery_app_backend.Dto.ReviewEntity.ReviewResposeDto;
import food_delivery_app_backend.Enum.GlobalRole;
import food_delivery_app_backend.Service.ReviewService.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review/food")
public class ReviewsController {
    private final ReviewService reviewService;

    public ReviewsController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/give")
    public ResponseEntity<ReviewResposeDto> addReview(Authentication authentication,@RequestBody ReviewRequstDto reviewRequstDto){
        ReviewResposeDto review=reviewService.giveReview(authentication,reviewRequstDto);
        return ResponseEntity.ok(review);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get/all/{id}")
    public ResponseEntity<List<ReviewRequstDto>> getAllReview(@PathVariable Long id){
        List<ReviewRequstDto> findAllReviews=reviewService.findAllReviewOfFood(id);
        return ResponseEntity.ok(findAllReviews);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get/{id}")
    public ResponseEntity<ReviewResposeDto> getOneReview(Authentication authentication, @PathVariable Long id){
        ReviewResposeDto reviewRequstDto=reviewService.getReview(authentication,id);
        return ResponseEntity.ok(reviewRequstDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/edit")
    public ResponseEntity<ReviewResposeDto> editDtoResponseEntity(Authentication authentication,@RequestBody ReviewEditDto reviewEditDto){
        ReviewResposeDto reviewRequstDto=reviewService.editReview(authentication,reviewEditDto);
        return ResponseEntity.ok(reviewRequstDto);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(Authentication authentication,@PathVariable Long id){
        reviewService.deleteReview(authentication,id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get/average/{id}")
    public ResponseEntity<Double> findAverageOfAFood(@PathVariable Long id){
       Double average= reviewService.getAverageRating(id);
       return ResponseEntity.ok(average);
    }
}
