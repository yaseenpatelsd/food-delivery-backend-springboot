package food_delivery_app_backend.Controller;

import food_delivery_app_backend.Dto.FoodDto.FoodEditDto;
import food_delivery_app_backend.Dto.FoodDto.FoodRequestDto;
import food_delivery_app_backend.Dto.FoodDto.FoodResponseDto;
import food_delivery_app_backend.Enum.VegNonVeg;
import food_delivery_app_backend.Service.FoodService.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business/food")
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/add")
    public ResponseEntity<FoodResponseDto> addFood(Authentication authentication, @RequestBody FoodRequestDto foodRequestDto){
        FoodResponseDto foodRequestDto1=foodService.foodAdd(authentication,foodRequestDto);
        return ResponseEntity.ok(foodRequestDto1);
    }


    @GetMapping("/search")
    public List<FoodResponseDto> search(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) Integer minAmount,
                                       @RequestParam(required = false) Integer maxAmount,
                                       @RequestParam(required = false) Double minRating ,
                                       @RequestParam(required = false) Double maxRating,
                                       @RequestParam(required = false) VegNonVeg vegType){
        List<FoodResponseDto> foodResponseDtos=foodService.getFood(name, minAmount, maxAmount, minRating, maxRating, vegType);
        return foodResponseDtos;
    }
    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/edit/{id}")
    public ResponseEntity<FoodRequestDto> editFood(Authentication authentication, @PathVariable Long id,@RequestBody FoodEditDto dto){
        FoodRequestDto foodRequestDto=foodService.editFood(authentication,id,dto);
        return ResponseEntity.ok(foodRequestDto);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFood(Authentication authentication,@PathVariable Long id){
        foodService.deleteFoodPost(authentication,id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/de-list/{id}")
    public ResponseEntity<String> diListFood(Authentication authentication,@PathVariable Long id){
        foodService.DilistProduct(authentication,id);
        return ResponseEntity.ok("Food is being deList it wont be visible to users you can enable it later if you want");
    }
    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/re-list/{id}")
    public ResponseEntity<String> foodREList(Authentication authentication,@PathVariable Long id){
        foodService.enableFood(authentication,id);
        return ResponseEntity.ok("Food is been listed");
    }

}
