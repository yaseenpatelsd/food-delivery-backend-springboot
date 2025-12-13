package food_delivery_app_backend.Entity.PersonalDetailEntity;

import food_delivery_app_backend.Entity.UserEntities.OwnerEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "owner_profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerPersonalProfileEntity extends GlobalPersonalDetailEntity{
    @OneToOne(mappedBy = "ownerPersonalProfileEntity")
    private OwnerEntity owner;

}
