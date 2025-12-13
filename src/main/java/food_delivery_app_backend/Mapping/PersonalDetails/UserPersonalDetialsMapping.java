package food_delivery_app_backend.Mapping.PersonalDetails;

import food_delivery_app_backend.Dto.PersonalProfileRequest.UserProfileRequestDto;
import food_delivery_app_backend.Entity.PersonalDetailEntity.UserPersonalProfileEntity;

public class UserPersonalDetialsMapping {

    public static UserPersonalProfileEntity toEntity(UserProfileRequestDto dto){
        if (dto==null)return null;

       UserPersonalProfileEntity userPersonalProfile=new UserPersonalProfileEntity();
       userPersonalProfile.setFullname(dto.getFullName());
       userPersonalProfile.setGender(dto.getGender());
       userPersonalProfile.setAddress(dto.getAddress());
       userPersonalProfile.setCity(dto.getCity());
       userPersonalProfile.setState(dto.getState());
       userPersonalProfile.setCountry(dto.getCountry());
       userPersonalProfile.setPinCode(dto.getPincode());
       userPersonalProfile.setMobileNo(dto.getMobileNumber());

       return userPersonalProfile;
    }
    public static UserProfileRequestDto toDto(UserPersonalProfileEntity entity){
        if (entity==null)return null;

        UserProfileRequestDto dto=new UserProfileRequestDto();
        dto.setFullName(entity.getFullname());
        dto.setGender(entity.getGender());
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setCountry(entity.getCountry());
        dto.setPincode(entity.getPinCode());
        dto.setMobileNumber(entity.getMobileNo());

        return dto;
    }
}
