package food_delivery_app_backend.Service.BusinessPartnerFlow;

import food_delivery_app_backend.Dto.BusinessPartnerFlow.BusinessDetailsEditDto;
import food_delivery_app_backend.Dto.BusinessPartnerFlow.BusinessPartnerRequestDto;
import food_delivery_app_backend.Dto.BusinessPartnerFlow.BusinessStatusChangeDto;
import food_delivery_app_backend.Entity.Business_Partner_Entity.BusinessPartnerEntity;
import food_delivery_app_backend.Entity.UserEntities.GlobalUserEntity;
import food_delivery_app_backend.Entity.UserEntities.OwnerEntity;
import food_delivery_app_backend.Enum.VegNonVeg;
import food_delivery_app_backend.Exeption.IllegalArgumentException;
import food_delivery_app_backend.Exeption.ResouceNotFoundException;
import food_delivery_app_backend.Exeption.SomethingIsWrongException;
import food_delivery_app_backend.Exeption.UserNotFoundException;
import food_delivery_app_backend.Mapping.BusinessPartnerMapping;
import food_delivery_app_backend.Repository.BusinessPartnerRepository;
import food_delivery_app_backend.Repository.Global.GlobalUserRepository;
import food_delivery_app_backend.Repository.Global.OwnerRepository;
import food_delivery_app_backend.Specification.BusinessPartner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessPartnerService {
    @Autowired
    private BusinessPartnerRepository businessPartnerRepository;
    @Autowired
    private OwnerRepository ownerRepository;


      @Autowired
    private GlobalUserRepository globalUserRepository;


  //add business partner
    public BusinessPartnerRequestDto addBusiness(BusinessPartnerRequestDto businessPartnerRequestDto,Authentication authentication){
        BusinessPartnerEntity businessPartnerEntity=BusinessPartnerMapping.toEntity(businessPartnerRequestDto);
        OwnerEntity ownerEntity=ownerRepository.findByUsername(authentication.getName())
                        .orElseThrow(()->new UserNotFoundException("Can't find owner account"));

        boolean isAlreadyHaveBusiness=businessPartnerRepository.existsByOwner_Username(ownerEntity.getUsername());

        if (isAlreadyHaveBusiness){
            throw new SomethingIsWrongException("Error you already have a business registered name "+ businessPartnerEntity.getName() +" As per our policy you cant operate multiple business on same id");
        }

        businessPartnerEntity.setOwner(ownerEntity);
        BusinessPartnerEntity saved=businessPartnerRepository.save(businessPartnerEntity);
        return BusinessPartnerMapping.toDto(saved);
    }

    //get business
    public List<BusinessPartnerRequestDto> getBusiness(String name, VegNonVeg vegCategory,String city){
        Specification<BusinessPartnerEntity> spec=null;

        if (name!=null){
            spec=(spec==null)? BusinessPartner.findByName(name): spec.and(BusinessPartner.findByName(name));
        }
        if (vegCategory!=null){
            spec=(spec==null) ? BusinessPartner.findByVegCategory(vegCategory) : spec.and(BusinessPartner.findByVegCategory(vegCategory));
        }
        if (city!=null){
            spec=(spec==null)? BusinessPartner.findByCity(city) :spec.and(BusinessPartner.findByCity(city));
        }

        List<BusinessPartnerEntity> businessPartnerEntities= (spec==null)? businessPartnerRepository.findAll() :businessPartnerRepository.findAll(spec);

        return businessPartnerEntities.stream().map(BusinessPartnerMapping::toDto).collect(Collectors.toList());
    }

    public BusinessPartnerRequestDto editBusiness(Authentication authentication,BusinessDetailsEditDto businessDetailsEditDto){
        OwnerEntity ownerEntity=user(authentication);
        BusinessPartnerEntity bn=businessPartner(ownerEntity.getUsername());



        roleVerification(bn,ownerEntity);

        bn.changeVariables(businessDetailsEditDto);

        businessPartnerRepository.save(bn);
        BusinessPartnerRequestDto businessDetailsEditDto1=BusinessPartnerMapping.toDto(bn);

        return businessDetailsEditDto1;
    }

    public void changeOpeningStatus(Authentication authentication,BusinessStatusChangeDto businessStatusChangeDto){
        BusinessPartnerEntity businessPartnerEntity= businessPartner(authentication.getName());


        OwnerEntity ownerEntity=user(authentication);

       roleVerification(businessPartnerEntity,ownerEntity);

        businessPartnerEntity.setStatus(businessStatusChangeDto.getStatus());
        businessPartnerRepository.save(businessPartnerEntity);
    }


    public void deleteBusiness(Authentication authentication){
       OwnerEntity ownerEntity=user(authentication);

       BusinessPartnerEntity businessPartner=businessPartnerRepository.findByOwner_Username(authentication.getName())
                       .orElseThrow(()->new UserNotFoundException("Can't find a owner account"));

        roleVerification(businessPartner,ownerEntity);

        businessPartnerRepository.delete(businessPartner);

    }

/*------------------------------------------helpter method----------------------------------------------------------------------*/

    public BusinessPartnerEntity businessPartner(String username){
        return businessPartnerRepository.findByOwner_Username(username)
                .orElseThrow(()-> new ResouceNotFoundException("Business not found by this id"));
    }

    public OwnerEntity user(Authentication authentication){
        return ownerRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UserNotFoundException("Can't find a account link to this username"));
    }

    public void roleVerification(BusinessPartnerEntity bn,GlobalUserEntity globalUserEntity){
        if (!globalUserEntity.getUsername().equals(bn.getOwner().getUsername())){
            throw new IllegalArgumentException("Not allowed to change others business");
        }
    }

}
