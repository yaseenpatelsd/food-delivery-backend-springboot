package food_delivery_app_backend.Entity.PersonalDetailEntity;

import food_delivery_app_backend.Enum.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "global_personal_data")
@Inheritance(strategy = InheritanceType.JOINED)
public class GlobalPersonalDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //personal details
    @Column(name ="fullName", nullable = false )
    private String fullname;
    @Column(name = "gender",nullable = false)
    private Gender gender;

    //address
    @Column(name = "address",nullable = false)
    private String address;
    @Column(name = "city",nullable = false)
    private String city;
    @Column(name = "state",nullable = false)
    private String state;
    @Column(name = "country",nullable = false)
    private String country;
    @Column(name = "pincode",nullable = false)
    private long pinCode;

    //contact info
    @Column(name = "mobileNo",nullable = false,unique = true)
    private String mobileNo;


}
