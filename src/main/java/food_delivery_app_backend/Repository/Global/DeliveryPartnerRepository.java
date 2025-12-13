package food_delivery_app_backend.Repository.Global;

import food_delivery_app_backend.Entity.UserEntities.DeliveryPartnerEntity;
import food_delivery_app_backend.Entity.UserEntities.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartnerEntity,Long> {
    Optional<DeliveryPartnerEntity> findByUsername(String username);

    List<DeliveryPartnerEntity> findByOccupiedTrueAndOccupiedFalse();

}
