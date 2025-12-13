package food_delivery_app_backend.Repository;

import food_delivery_app_backend.Entity.Business_Partner_Entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FoodRepository extends JpaRepository<FoodEntity,Long>, JpaSpecificationExecutor<FoodEntity> {
    List<FoodEntity> findByRazorpayOrders_User_Username(String username);
}
