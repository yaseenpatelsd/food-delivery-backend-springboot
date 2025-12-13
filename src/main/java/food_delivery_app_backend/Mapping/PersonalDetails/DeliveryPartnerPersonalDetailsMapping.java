package food_delivery_app_backend.Mapping.PersonalDetails;

import food_delivery_app_backend.Dto.PersonalProfileRequest.DeliveryPartnerProfileRequestDto;
import food_delivery_app_backend.Entity.PersonalDetailEntity.DeliveryPersonalProfileEntity;


public class DeliveryPartnerPersonalDetailsMapping {

    public static DeliveryPersonalProfileEntity toEntity(DeliveryPartnerProfileRequestDto dto){

        DeliveryPersonalProfileEntity entity=new DeliveryPersonalProfileEntity();
        entity.setFullname(dto.getFullName());
        entity.setGender(dto.getGender());
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setPinCode(dto.getPincode());
        entity.setMobileNo(dto.getMobileNumber());
        entity.setLicenceNo(dto.getLicenceNo());

        return entity;
    }

    public static DeliveryPartnerProfileRequestDto toDto(DeliveryPersonalProfileEntity entity){
        if (entity==null)return  null;

        DeliveryPartnerProfileRequestDto dto=new DeliveryPartnerProfileRequestDto();
        dto.setFullName(entity.getFullname());
        dto.setGender(entity.getGender());
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setCountry(entity.getCountry());
        dto.setPincode(entity.getPinCode());
        dto.setMobileNumber(entity.getMobileNo());
        dto.setLicenceNo(entity.getLicenceNo());

        return dto;
    }
}
