package food_delivery_app_backend.Controller.PersonalDetails;

import food_delivery_app_backend.Dto.PersonalProfileRequest.DeliveryPartnerProfileRequestDto;
import food_delivery_app_backend.Dto.PersonalProfileResponse.DeliveryPartnerProfileResponseDto;
import food_delivery_app_backend.Service.PersonalDetailService.DeliveryPartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery/partner/profile")
public class DeliveryPartnerProfileController {
    private final DeliveryPartnerService deliveryPartnerService;

    public DeliveryPartnerProfileController(DeliveryPartnerService deliveryPartnerService) {
        this.deliveryPartnerService = deliveryPartnerService;
    }

     @PreAuthorize("hasRole('DELIVERY_PARTNER')")
    @PostMapping
    public ResponseEntity<DeliveryPartnerProfileResponseDto> createProfile(@RequestBody DeliveryPartnerProfileRequestDto dto,Authentication authentication){
        DeliveryPartnerProfileResponseDto responseDto= deliveryPartnerService.createProfile(dto,authentication);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasRole('DELIVERY_PARTNER')")
    @PatchMapping
    public ResponseEntity<DeliveryPartnerProfileResponseDto> updateProfile(Authentication authentication, @RequestBody DeliveryPartnerProfileRequestDto dto){
        DeliveryPartnerProfileResponseDto responseDto=deliveryPartnerService.updateProfile(dto,authentication);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasRole('DELIVERY_PARTNER')")
    @GetMapping
    public ResponseEntity<DeliveryPartnerProfileResponseDto> getProfile(Authentication authentication){
        DeliveryPartnerProfileResponseDto responseDto= deliveryPartnerService.getProfile(authentication);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasRole('DELIVERY_PARTNER')")
    @DeleteMapping
    public ResponseEntity<?> deleteProfile(Authentication authentication){
        deliveryPartnerService.deleteProfile(authentication);
        return ResponseEntity.noContent().build();

    }
}
