package food_delivery_app_backend.Mapping;

import food_delivery_app_backend.Dto.BusinessPartnerFlow.BusinessPartnerRequestDto;
import food_delivery_app_backend.Entity.Business_Partner_Entity.BusinessPartnerEntity;
import food_delivery_app_backend.Enum.BusinessStatus;
import org.springframework.stereotype.Component;

@Component
public class BusinessPartnerMapping {

    public static BusinessPartnerEntity toEntity(BusinessPartnerRequestDto dto){
       if (dto==null)return null;

       BusinessPartnerEntity businessPartner=new BusinessPartnerEntity();
      businessPartner.setName(dto.getName());
      businessPartner.setBusinessType(dto.getBusinessType());
      businessPartner.setAddress(dto.getAddress());
      businessPartner.setCity(dto.getCity());
      businessPartner.setState(dto.getState());
      businessPartner.setCountry(dto.getCountry());
      businessPartner.setStatus(dto.getStatus());
      businessPartner.setCuisineType(dto.getCuisineType());
      businessPartner.setIsActive(dto.getIsActive());
      businessPartner.setFssaiNumber(dto.getFssaiNumber());
      businessPartner.setVegType(dto.getVegType());

      return businessPartner;
    }

    public static BusinessPartnerRequestDto toDto(BusinessPartnerEntity entity){
        if (entity==null) return null;

        BusinessPartnerRequestDto dto=new BusinessPartnerRequestDto();
        dto.setName(entity.getName());
        dto.setBusinessType(entity.getBusinessType());
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setCountry(entity.getCountry());
        dto.setStatus(entity.getStatus());
        dto.setCuisineType(entity.getCuisineType());
        dto.setIsActive(entity.getIsActive());
        dto.setFssaiNumber(entity.getFssaiNumber());
        dto.setVegType(entity.getVegType());


        return dto;
    }
}
