package food_delivery_app_backend.Entity.UserEntities;

import food_delivery_app_backend.Entity.Business_Partner_Entity.BusinessPartnerEntity;
import food_delivery_app_backend.Entity.PersonalDetailEntity.OwnerPersonalProfileEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "owner_entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerEntity extends GlobalUserEntity{
    @OneToOne(mappedBy = "owner")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private BusinessPartnerEntity businessPartner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_profile_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private OwnerPersonalProfileEntity ownerPersonalProfileEntity;

}
