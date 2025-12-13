package food_delivery_app_backend.Repository.PersonalDetail;

import food_delivery_app_backend.Entity.PersonalDetailEntity.OwnerPersonalProfileEntity;
import food_delivery_app_backend.Entity.PersonalDetailEntity.UserPersonalProfileEntity;
import food_delivery_app_backend.Entity.UserEntities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPersonalDetailsRepository extends JpaRepository<UserPersonalProfileEntity,Long> {

    Optional<UserPersonalProfileEntity> findByUser(UserEntity user);
    Optional<UserPersonalProfileEntity> findByUser_Username(String username);
}
