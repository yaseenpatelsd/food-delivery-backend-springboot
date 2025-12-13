package food_delivery_app_backend.Entity.UserEntities;

import food_delivery_app_backend.Entity.PersonalDetailEntity.OwnerPersonalProfileEntity;
import food_delivery_app_backend.Entity.PersonalDetailEntity.UserPersonalProfileEntity;
import food_delivery_app_backend.Entity.ReviewEntity.ReviewEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends GlobalUserEntity {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id")
    private UserPersonalProfileEntity userPersonalProfileEntity;
}
