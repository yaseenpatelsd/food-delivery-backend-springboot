package food_delivery_app_backend.Service.PersonalDetailService;

import food_delivery_app_backend.Dto.PersonalProfileRequest.DeliveryPartnerProfileRequestDto;
import food_delivery_app_backend.Dto.PersonalProfileRequest.OwnerProfileRequestDto;
import food_delivery_app_backend.Dto.PersonalProfileResponse.DeliveryPartnerProfileResponseDto;
import food_delivery_app_backend.Dto.PersonalProfileResponse.OwnerProfileResponseDto;
import food_delivery_app_backend.Entity.PersonalDetailEntity.DeliveryPersonalProfileEntity;
import food_delivery_app_backend.Entity.PersonalDetailEntity.OwnerPersonalProfileEntity;
import food_delivery_app_backend.Entity.UserEntities.DeliveryPartnerEntity;
import food_delivery_app_backend.Entity.UserEntities.OwnerEntity;
import food_delivery_app_backend.Exeption.UserNotFoundException;
import food_delivery_app_backend.Mapping.PersonalDetails.DeliveryPartnerPersonalDetailsMapping;
import food_delivery_app_backend.Repository.Global.DeliveryPartnerRepository;
import food_delivery_app_backend.Repository.PersonalDetail.DeliveryPartnerPersonalDetailRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryPartnerService extends AbstractionProfileService<DeliveryPartnerProfileRequestDto, DeliveryPersonalProfileEntity, DeliveryPartnerProfileResponseDto>{
    private final DeliveryPartnerPersonalDetailRepository repository;
    private final DeliveryPartnerRepository deliveryPartnerRepository;

    public DeliveryPartnerService(DeliveryPartnerPersonalDetailRepository repo, DeliveryPartnerRepository deliveryPartnerRepository) {
        super(repo);
        this.repository = repo;
        this.deliveryPartnerRepository = deliveryPartnerRepository;
    }

    @Override
    protected DeliveryPersonalProfileEntity mapToEntity(DeliveryPartnerProfileRequestDto dto) {
        return DeliveryPartnerPersonalDetailsMapping.toEntity(dto);
    }
    @Override
    protected Optional<DeliveryPersonalProfileEntity> findEntityByUsername(String username) {
        return repository.findByDeliveryPartner_Username(username);
    }

    @Override
    protected void detachProfileFromUser(String username){
        DeliveryPartnerEntity deliveryPartner=deliveryPartnerRepository.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException("Can't find a account"));

        deliveryPartner.setDeliveryPersonalProfileEntity(null);

        deliveryPartnerRepository.save(deliveryPartner);
    }

    @Override
    protected DeliveryPartnerProfileResponseDto mapToDto(DeliveryPersonalProfileEntity entity) {
        DeliveryPartnerProfileResponseDto resp=new DeliveryPartnerProfileResponseDto();
        resp.setFullName(entity.getFullname());
        resp.setGender(entity.getGender());
        resp.setAddress(entity.getAddress());
        resp.setCity(entity.getCity());
        resp.setState(entity.getState());
        resp.setCountry(entity.getCountry());
        resp.setPincode(entity.getPinCode());
        resp.setMobileNumber(entity.getMobileNo());

        resp.setLicenceNo(entity.getLicenceNo());
        return resp;
    }

    @Override
    protected void mapTOExistingEntity(DeliveryPersonalProfileEntity entity, DeliveryPartnerProfileRequestDto dto) {
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

    public DeliveryPartnerProfileResponseDto createProfile(DeliveryPartnerProfileRequestDto req, Authentication auth) {

        DeliveryPartnerEntity deliveryPartner = deliveryPartnerRepository.findByUsername(auth.getName())
                .orElseThrow(()->new UserNotFoundException("Account not found"));

        DeliveryPersonalProfileEntity profile = mapToEntity(req);

        // Set BOTH sides
        deliveryPartner.setDeliveryPersonalProfileEntity(profile);
        profile.setDeliveryPartner(deliveryPartner);
        profile.setLicenceNo(req.getLicenceNo());

        deliveryPartnerRepository.save(deliveryPartner);
        repository.save(profile);

        return mapToDto(profile);
    }

}
