package food_delivery_app_backend.Repository.Global;

import food_delivery_app_backend.Entity.UserEntities.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<OwnerEntity,Long> {
    Optional<OwnerEntity> findByUsername(String username);
}
