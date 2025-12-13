package food_delivery_app_backend.Service.PersonalDetailService;

import food_delivery_app_backend.Dto.PersonalProfileRequest.UserProfileRequestDto;
import food_delivery_app_backend.Dto.PersonalProfileResponse.UserProfileResponseDto;
import food_delivery_app_backend.Entity.PersonalDetailEntity.UserPersonalProfileEntity;
import food_delivery_app_backend.Entity.UserEntities.UserEntity;
import food_delivery_app_backend.Exeption.UserNotFoundException;
import food_delivery_app_backend.Mapping.PersonalDetails.UserPersonalDetialsMapping;
import food_delivery_app_backend.Repository.Global.UserRepository;
import food_delivery_app_backend.Repository.PersonalDetail.UserPersonalDetailsRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends AbstractionProfileService<UserProfileRequestDto, UserPersonalProfileEntity, UserProfileResponseDto>{

    private final UserPersonalDetailsRepository userPersonalDetailsRepository;
    private final UserRepository userRepository;
    public UserProfileService(UserPersonalDetailsRepository repo, UserRepository userRepository) {
     super(repo);
     this.userPersonalDetailsRepository=repo;
        this.userRepository = userRepository;
    }

    @Override
    protected UserPersonalProfileEntity mapToEntity(UserProfileRequestDto dto) {
        return UserPersonalDetialsMapping.toEntity(dto);
    }
    @Override
    protected Optional<UserPersonalProfileEntity> findEntityByUsername(String username) {
        return userPersonalDetailsRepository.findByUser_Username(username);
    }

    @Override
    protected void detachProfileFromUser(String username) {
        UserEntity user=userRepository.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException("Cant find a account"));

        user.setUserPersonalProfileEntity(null);
        userRepository.save(user);
    }


    @Override
    protected UserProfileResponseDto mapToDto(UserPersonalProfileEntity entity) {
        UserProfileResponseDto resp=new UserProfileResponseDto();
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
    protected void mapTOExistingEntity(UserPersonalProfileEntity entity, UserProfileRequestDto dto) {
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
    public UserProfileResponseDto createProfile(UserProfileRequestDto req, Authentication auth) {

        UserEntity user = userRepository.findByUsername(auth.getName())
                .orElseThrow(()->new UserNotFoundException("Account not found"));

        UserPersonalProfileEntity profile = mapToEntity(req);

        // Set BOTH sides
        user.setUserPersonalProfileEntity(profile);
        profile.setUser(user);

        userRepository.save(user);
        userPersonalDetailsRepository.save(profile);

        return mapToDto(profile);
    }

}
