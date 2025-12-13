package food_delivery_app_backend.Entity.PersonalDetailEntity;

import food_delivery_app_backend.Entity.UserEntities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPersonalProfileEntity extends GlobalPersonalDetailEntity{
    @OneToOne(mappedBy = "userPersonalProfileEntity")
    private UserEntity user;
}
