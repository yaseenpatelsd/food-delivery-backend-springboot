package food_delivery_app_backend.Mapping.PersonalDetails;

import food_delivery_app_backend.Dto.PersonalProfileRequest.OwnerProfileRequestDto;
import food_delivery_app_backend.Entity.PersonalDetailEntity.OwnerPersonalProfileEntity;

public class OwnerPersonalDetailMapping {

    public static OwnerPersonalProfileEntity toEntity(OwnerProfileRequestDto dto){
        if (dto==null)return null;

        OwnerPersonalProfileEntity entity=new OwnerPersonalProfileEntity();
        entity.setFullname(dto.getFullName());
        entity.setGender(dto.getGender());
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPinCode(dto.getPincode());
        entity.setMobileNo(dto.getMobileNumber());

        return entity;
    }

    public static OwnerProfileRequestDto toDto(OwnerPersonalProfileEntity entity){
        if (entity==null)return  null;

        OwnerProfileRequestDto dto=new OwnerProfileRequestDto();
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
