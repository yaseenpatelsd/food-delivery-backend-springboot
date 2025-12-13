package food_delivery_app_backend.Repository.Global;

import food_delivery_app_backend.Entity.UserEntities.GlobalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface GlobalUserRepository extends JpaRepository<GlobalUserEntity,Long> , JpaSpecificationExecutor<GlobalUserEntity> {
    Optional<GlobalUserEntity> findByUsername(String username);
}
