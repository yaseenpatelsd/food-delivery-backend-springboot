package food_delivery_app_backend.Repository;

import food_delivery_app_backend.Entity.Order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity,Long>{
    List<OrderEntity> findByUser_Username(String username);
}
