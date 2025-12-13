package food_delivery_app_backend.Controller.PersonalDetails;

import food_delivery_app_backend.Dto.PersonalProfileRequest.OwnerProfileRequestDto;
import food_delivery_app_backend.Dto.PersonalProfileRequest.UserProfileRequestDto;
import food_delivery_app_backend.Dto.PersonalProfileResponse.OwnerProfileResponseDto;
import food_delivery_app_backend.Dto.PersonalProfileResponse.UserProfileResponseDto;
import food_delivery_app_backend.Service.PersonalDetailService.OwnerProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner/profile")
public class OwnerProfileController {
    private final OwnerProfileService ownerProfileService;

    public OwnerProfileController(OwnerProfileService ownerProfileService) {
        this.ownerProfileService = ownerProfileService;
    }
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public ResponseEntity<OwnerProfileResponseDto> createProfile(@RequestBody OwnerProfileRequestDto dto,Authentication authentication){
        OwnerProfileResponseDto responseDto= ownerProfileService.createProfile(dto,authentication);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping
    public ResponseEntity<OwnerProfileResponseDto> updateProfile(Authentication authentication, @RequestBody OwnerProfileRequestDto dto){
        OwnerProfileResponseDto responseDto=ownerProfileService.updateProfile(dto,authentication);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping
    public ResponseEntity<OwnerProfileResponseDto> getProfile(Authentication authentication){
        OwnerProfileResponseDto responseDto= ownerProfileService.getProfile(authentication);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping
   public ResponseEntity<?> deleteProfile(Authentication authentication){
        ownerProfileService.deleteProfile(authentication);
        return ResponseEntity.noContent().build();

    }
}
