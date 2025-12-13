package food_delivery_app_backend.Service.PersonalDetailService;

import food_delivery_app_backend.Dto.PersonalProfileRequest.OwnerProfileRequestDto;
import food_delivery_app_backend.Dto.PersonalProfileRequest.UserProfileRequestDto;
import food_delivery_app_backend.Dto.PersonalProfileResponse.OwnerProfileResponseDto;
import food_delivery_app_backend.Dto.PersonalProfileResponse.UserProfileResponseDto;
import food_delivery_app_backend.Entity.PersonalDetailEntity.OwnerPersonalProfileEntity;
import food_delivery_app_backend.Entity.PersonalDetailEntity.UserPersonalProfileEntity;
import food_delivery_app_backend.Entity.UserEntities.OwnerEntity;
import food_delivery_app_backend.Entity.UserEntities.UserEntity;
import food_delivery_app_backend.Exeption.UserNotFoundException;
import food_delivery_app_backend.Mapping.PersonalDetails.OwnerPersonalDetailMapping;
import food_delivery_app_backend.Repository.Global.OwnerRepository;
import food_delivery_app_backend.Repository.PersonalDetail.OwnerPersonalDetailRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerProfileService extends AbstractionProfileService<OwnerProfileRequestDto,OwnerPersonalProfileEntity, OwnerProfileResponseDto>{
   private final OwnerPersonalDetailRepository personalDetailRepository;
   private final OwnerRepository ownerRepository;

    public OwnerProfileService(OwnerPersonalDetailRepository repo, OwnerRepository ownerRepository) {
        super(repo);
        this.personalDetailRepository = repo;
        this.ownerRepository = ownerRepository;
    }

    @Override
    protected OwnerPersonalProfileEntity mapToEntity(OwnerProfileRequestDto dto) {
       return OwnerPersonalDetailMapping.toEntity(dto);
    }
    @Override
    protected Optional<OwnerPersonalProfileEntity> findEntityByUsername(String username) {
        return personalDetailRepository.findByOwner_Username(username);
    }

    @Override
    protected void detachProfileFromUser(String username) {
        OwnerEntity ownerEntity=ownerRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("Owner Account not found"));

        ownerEntity.setOwnerPersonalProfileEntity(null);
        ownerRepository.save(ownerEntity);
    }


    @Override
    protected OwnerProfileResponseDto mapToDto(OwnerPersonalProfileEntity entity) {
        OwnerProfileResponseDto resp=new OwnerProfileResponseDto();
        resp.setFullName(entity.getFullname());
        resp.setGender(entity.getGender());
        resp.setAddress(entity.getAddress());
        resp.setCity(entity.getCity());
        resp.setState(entity.getState());
        resp.setCountry(entity.getCountry());
        resp.setPincode(entity.getPinCode());
        resp.setMobileNumber(entity.getMobileNo());

        return resp;
    }

    @Override
    protected void mapTOExistingEntity(OwnerPersonalProfileEntity entity, OwnerProfileRequestDto dto) {
        if (dto.getFullName()!=null){
            entity.setFullname(dto.getFullName());
        }
        if (dto.getGender()!=null){
            entity.setGender(dto.getGender());
        }
        if (dto.getAddress()!=null){
            entity.setAddress(dto.getAddress());
        }
        if (dto.getCity()!=null){
            entity.setCity(dto.getCity());
        }
        if (dto.getState()!=null){
            entity.setState(dto.getState());

        }
        if (dto.getCountry()!=null){
            entity.setCountry(dto.getCountry());
        }
        if (dto.getPincode()!=null){
            entity.setPinCode(dto.getPincode());
        }
        if (dto.getMobileNumber()!=null){
            entity.setMobileNo(dto.getMobileNumber());
        }
    }


    public OwnerProfileResponseDto createProfile(OwnerProfileRequestDto req, Authentication auth) {

        OwnerEntity ownerEntity = ownerRepository.findByUsername(auth.getName())
                .orElseThrow(()->new UserNotFoundException("Account not found"));

        OwnerPersonalProfileEntity profile = mapToEntity(req);

        // Set BOTH sides
        ownerEntity.setOwnerPersonalProfileEntity(profile);
        profile.setOwner(ownerEntity);

        ownerRepository.save(ownerEntity);
        personalDetailRepository.save(profile);

        return mapToDto(profile);
    }

}
