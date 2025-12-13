package food_delivery_app_backend.Repository;

import food_delivery_app_backend.Entity.Order.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemEntityRepository extends JpaRepository<OrderItemEntity,Long> {
}
