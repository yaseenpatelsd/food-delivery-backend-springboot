package food_delivery_app_backend.Entity.Business_Partner_Entity;

import food_delivery_app_backend.Dto.FoodDto.FoodEditDto;
import food_delivery_app_backend.Entity.RazorPayEntity.RazorpayEntity;
import food_delivery_app_backend.Entity.ReviewEntity.ReviewEntity;
import food_delivery_app_backend.Enum.VegNonVeg;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "food_entity")
@Builder
public class FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "amount",nullable = false)
    private Integer amount;

    @Column(name = "type",nullable = false)
    @Enumerated(EnumType.STRING)
    private VegNonVeg type;
    @Column(name = "isAvailable", nullable = false)
    private Boolean isAvailable;

    @Column(name = "image",nullable = false)
    private String image;

    @Column(name = "category" , nullable = false)
    private String category;

    @Column(name = "averageRating")
    private Double averageRating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_partner_id")
    private BusinessPartnerEntity businessPartner;



    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "food")
    private List<RazorpayEntity> razorpayOrders;

    /*-------------------------------------changing values---------------------------------------------*/

    public void changeValues(FoodEditDto dto){
        if (dto.getName()!=null){
            this.setName(dto.getName());
        }
        if (dto.getDescription()!=null){
            this.setDescription(dto.getDescription());
        }
        if (dto.getAmount()!=null){
            this.setAmount(dto.getAmount());
        }
        if (dto.getType()!=null){
            this.setType(dto.getType());
        }
        if (dto.getIsAvailable()!=null){
            this.setIsAvailable(dto.getIsAvailable());
        }
        if (dto.getImage()!=null){
            this.setImage(dto.getImage());
        }
        if (dto.getCategory()!=null){
            this.setCategory(dto.getCategory());
        }
    }

}
