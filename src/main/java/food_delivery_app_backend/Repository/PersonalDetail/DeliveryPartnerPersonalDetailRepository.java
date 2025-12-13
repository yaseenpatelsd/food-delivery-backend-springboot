package food_delivery_app_backend.Repository.PersonalDetail;

import food_delivery_app_backend.Entity.PersonalDetailEntity.DeliveryPersonalProfileEntity;
import food_delivery_app_backend.Entity.PersonalDetailEntity.OwnerPersonalProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryPartnerPersonalDetailRepository extends JpaRepository<DeliveryPersonalProfileEntity,Long> {
    Optional<DeliveryPersonalProfileEntity> findByDeliveryPartner_Username(String username);
}
