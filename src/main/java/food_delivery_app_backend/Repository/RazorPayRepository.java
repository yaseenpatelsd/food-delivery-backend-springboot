package food_delivery_app_backend.Repository;

import food_delivery_app_backend.Entity.RazorPayEntity.RazorpayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RazorPayRepository extends JpaRepository<RazorpayEntity,Long> {

    List<RazorpayEntity> findByUser_Username(String username);
}
