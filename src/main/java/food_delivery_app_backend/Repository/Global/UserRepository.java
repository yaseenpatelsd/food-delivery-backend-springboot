package food_delivery_app_backend.Repository.Global;

import food_delivery_app_backend.Entity.UserEntities.OwnerEntity;
import food_delivery_app_backend.Entity.UserEntities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
}
