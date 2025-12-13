package food_delivery_app_backend.Entity.UserEntities;

import food_delivery_app_backend.Entity.PersonalDetailEntity.DeliveryPersonalProfileEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "delivery_partner_entity")
public class DeliveryPartnerEntity extends GlobalUserEntity{
    @Column(name = "vehicle_no",nullable = false)
    private String vehicleNo;

    @Column(name = "onduty",nullable = false)
    private Boolean onDuty;
    @Column(name = "occupied",nullable = false)
    private Boolean occupied;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id") // FK column
    private DeliveryPersonalProfileEntity deliveryPersonalProfileEntity;


}
