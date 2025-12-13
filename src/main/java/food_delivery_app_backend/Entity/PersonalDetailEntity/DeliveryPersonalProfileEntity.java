package food_delivery_app_backend.Entity.PersonalDetailEntity;

import food_delivery_app_backend.Entity.UserEntities.DeliveryPartnerEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery_partner_profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPersonalProfileEntity extends GlobalPersonalDetailEntity{
    @Column(name = "licenceNo",nullable = false)
    private String licenceNo;
    @OneToOne(mappedBy = "deliveryPersonalProfileEntity")
    private DeliveryPartnerEntity deliveryPartner;



}
