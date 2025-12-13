package food_delivery_app_backend.Entity.Business_Partner_Entity;

import food_delivery_app_backend.Dto.BusinessPartnerFlow.BusinessDetailsEditDto;
import food_delivery_app_backend.Entity.UserEntities.OwnerEntity;
import food_delivery_app_backend.Enum.BusinessStatus;
import food_delivery_app_backend.Enum.BusinessType;
import food_delivery_app_backend.Enum.VegNonVeg;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "business_partner_entity")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BusinessPartnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name" ,nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "businessType" ,nullable = false)
    private BusinessType businessType;

    @Column(name = "address" ,nullable = false)
    private String address;
    @Column(name = "city" ,nullable = false)
    private String city;
    @Column(name = "state" ,nullable = false)
    private String state;
    @Column(name = "country" ,nullable = false)
    private String country;

    @Column(name = "is_active" ,nullable = false)
    private Boolean isActive;
    @Enumerated(EnumType.STRING)
    @Column(name = "is_open" ,nullable = false)
    private BusinessStatus status;


    @Column(name = "cuisine_type" ,nullable = false)
    private String cuisineType;
    @Column(name = "fssai_number" ,nullable = false)
    private String fssaiNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "food_tyoe",nullable = false)
    private VegNonVeg vegType;


    @OneToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private OwnerEntity owner;


/*-------------------------------------------change method-------------------------------------------------------*/

    public void changeVariables(BusinessDetailsEditDto dto){
        if (dto.getName()!=null){
           this.setName(dto.getName());
        }
        if (dto.getBusinessType()!=null){
            this.setBusinessType(dto.getBusinessType());
        }
        if (dto.getAddress()!=null){
            this.setAddress(dto.getAddress());
        }
        if (dto.getCity()!=null){
            this.setCity(dto.getCity());
        }
        if (dto.getState()!=null){
            this.setState(dto.getState());
        }
        if (dto.getCountry()!=null){
            this.setCountry(dto.getCountry());
        }
        if (dto.getIsActive()!=null){
            this.setIsActive(dto.getIsActive());
        }
        if (dto.getStatus()!=null){
            this.setStatus(dto.getStatus());
        }
        if (dto.getCuisineType()!=null){
            this.setCuisineType(dto.getCuisineType());
        }
        if (dto.getFssaiNumber()!=null){
            this.setFssaiNumber(dto.getFssaiNumber());
        }
        if (dto.getVegType()!=null){
            this.setVegType(dto.getVegType());
        }
    }

}
