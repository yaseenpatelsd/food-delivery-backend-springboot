package food_delivery_app_backend.Repository;

import food_delivery_app_backend.Entity.Business_Partner_Entity.BusinessPartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BusinessPartnerRepository extends JpaRepository<BusinessPartnerEntity,Long>, JpaSpecificationExecutor<BusinessPartnerEntity> {

    Optional<BusinessPartnerEntity> findByOwner_Username(String username);
    Boolean existsByOwner_Username(String username);
}
