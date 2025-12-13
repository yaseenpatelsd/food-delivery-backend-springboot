package food_delivery_app_backend.Service.FoodService;

import food_delivery_app_backend.Dto.FoodDto.FoodEditDto;
import food_delivery_app_backend.Dto.FoodDto.FoodRequestDto;
import food_delivery_app_backend.Dto.FoodDto.FoodResponseDto;
import food_delivery_app_backend.Entity.Business_Partner_Entity.BusinessPartnerEntity;
import food_delivery_app_backend.Entity.Business_Partner_Entity.FoodEntity;
import food_delivery_app_backend.Entity.UserEntities.GlobalUserEntity;
import food_delivery_app_backend.Enum.VegNonVeg;
import food_delivery_app_backend.Exeption.IllegalArgumentException;
import food_delivery_app_backend.Exeption.ResouceNotFoundException;
import food_delivery_app_backend.Exeption.UserNotFoundException;
import food_delivery_app_backend.Mapping.FoodMapping;
import food_delivery_app_backend.Repository.BusinessPartnerRepository;
import food_delivery_app_backend.Repository.FoodRepository;
import food_delivery_app_backend.Repository.Global.GlobalUserRepository;
import food_delivery_app_backend.Specification.FoodSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private GlobalUserRepository globalUserRepository;
    @Autowired
    private BusinessPartnerRepository businessPartnerRepository;

    public FoodResponseDto foodAdd(Authentication authentication, FoodRequestDto dto){
        GlobalUserEntity globalUserEntity=globalUser(authentication);

        BusinessPartnerEntity businessPartnerEntity=businessPartnerRepository.findByOwner_Username(globalUserEntity.getUsername())
                .orElseThrow(()-> new ResouceNotFoundException("Business cant found food must be link to a business"));


        FoodEntity foodEntity= FoodMapping.toEntity(dto);
        foodEntity.setBusinessPartner(businessPartnerEntity);

        FoodResponseDto foodResponseDto=FoodResponseDto.builder()
                .name(foodEntity.getName())
                .description(foodEntity.getDescription())
                .amount(foodEntity.getAmount())
                .type(foodEntity.getType())
                .image(foodEntity.getImage())
                .isAvailable(foodEntity.getIsAvailable())
                .businessPartnerName(foodEntity.getBusinessPartner().getName())
                .businessPartnerAddress(foodEntity.getBusinessPartner().getAddress())
                .category(foodEntity.getCategory())
                .averageRating(foodEntity.getAverageRating())
                .build();
        FoodEntity saved=foodRepository.save(foodEntity);
        return foodResponseDto;
    }

    public List<FoodResponseDto> getFood(String name, Integer minAmount, Integer maxAmount, Double minRating , Double maxRating, VegNonVeg vegType){
        Specification<FoodEntity> spec=null;

        if (name!=null){
            spec=(spec==null)? FoodSpecification.findByName(name) : spec.and(FoodSpecification.findByName(name));
        }

        if (minAmount!=null || maxAmount!=null){
            spec=(spec==null)?FoodSpecification.findByPrice(minAmount, maxAmount): spec.and(FoodSpecification.findByPrice(minAmount, maxAmount));
        }

        if (minRating!=null || maxRating!=null){
            spec=(spec==null)? FoodSpecification.findByRating(minRating, maxRating): spec.and(FoodSpecification.findByRating(minRating, maxRating));
        }
        if (vegType!=null){
            spec=(spec==null)? FoodSpecification.findByVegNonVed(vegType): spec.and(FoodSpecification.findByVegNonVed(vegType));
        }

        spec=(spec==null)? FoodSpecification.filterIsAvailable(true)
                : spec.and(FoodSpecification.filterIsAvailable(true));


        List<FoodEntity> foodEntities =
                (spec == null) ? foodRepository.findAll(): foodRepository.findAll(spec);

        List<FoodResponseDto> foodResponseDtos=foodEntities.stream().map(FoodMapping::toResponseDto).collect(Collectors.toList());

        return foodResponseDtos;
    }


    public FoodRequestDto editFood(Authentication authentication,Long id , FoodEditDto dto){
        FoodEntity foodEntity=foodEntity(id);

        GlobalUserEntity user= globalUser(authentication);


        confirmingTheRoleNotNull(foodEntity);

        confirmingUserRoleInFood(authentication,foodEntity);


       foodEntity.changeValues(dto);

        FoodEntity foodEntity1=foodRepository.save(foodEntity);

        return FoodMapping.toDto(foodEntity1);
    }

    public void deleteFoodPost(Authentication authentication,Long id){
       FoodEntity foodEntity= foodEntity(id);


       confirmingTheRoleNotNull(foodEntity);
       confirmingUserRoleInFood(authentication,foodEntity);

        foodRepository.delete(foodEntity);
    }


    public void DilistProduct(Authentication authentication,Long id){
       FoodEntity foodEntity= foodEntity(id);

       confirmingTheRoleNotNull(foodEntity);
       confirmingUserRoleInFood(authentication,foodEntity);

       foodEntity.setIsAvailable(false);

       foodRepository.save(foodEntity);

    }

    public void enableFood(Authentication authentication,Long id){
        FoodEntity foodEntity=foodEntity(id);

        confirmingTheRoleNotNull(foodEntity);
        confirmingUserRoleInFood(authentication,foodEntity);

        foodEntity.setIsAvailable(true);
        foodRepository.save(foodEntity);
    }
    /*---------------------------------helper methods------------------------------------------------------------*/

    public GlobalUserEntity globalUser(Authentication authentication){
        return globalUserRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new UserNotFoundException("Account can't be find with this username"));
    }

    public FoodEntity foodEntity(Long id){
        return foodRepository.findById(id)
                .orElseThrow(()->new ResouceNotFoundException("Can't find the food by this id"));
    }


    public void confirmingTheRoleNotNull(FoodEntity foodEntity) {
        if (foodEntity.getBusinessPartner()==null|| foodEntity.getBusinessPartner().getOwner()==null){
            throw new IllegalArgumentException("Cant find Business details");
        }
    }

    public void confirmingUserRoleInFood(Authentication authentication,FoodEntity foodEntity){
        if (!authentication.getName().equals(foodEntity.getBusinessPartner().getOwner().getUsername())){
            throw new IllegalArgumentException("Not allowed to delete this post not verified");
        }
    }
}
