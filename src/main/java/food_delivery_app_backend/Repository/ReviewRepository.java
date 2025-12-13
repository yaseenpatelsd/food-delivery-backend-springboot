package food_delivery_app_backend.Repository;

import food_delivery_app_backend.Entity.Business_Partner_Entity.FoodEntity;
import food_delivery_app_backend.Entity.ReviewEntity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {



    List<ReviewEntity> findByFood(FoodEntity food);
    ReviewEntity findFirstByFood(FoodEntity food);
}
