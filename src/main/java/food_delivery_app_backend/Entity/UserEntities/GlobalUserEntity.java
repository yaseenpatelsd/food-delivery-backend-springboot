package food_delivery_app_backend.Entity.UserEntities;

import food_delivery_app_backend.Enum.GlobalRole;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "global_entity")
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GlobalUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "username", nullable = false,unique = true)
    private String username;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "email", nullable = false,unique = true)
    private String email;

    private String otp;
    private Long otpExpire;
    private Boolean isActive;

    @Column(name = "role",nullable = false)
    @Enumerated(EnumType.STRING)
    private GlobalRole role;

}
