package food_delivery_app_backend.Controller.PersonalDetails;

import food_delivery_app_backend.Dto.PersonalProfileRequest.UserProfileRequestDto;
import food_delivery_app_backend.Dto.PersonalProfileResponse.UserProfileResponseDto;
import food_delivery_app_backend.Service.PersonalDetailService.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<UserProfileResponseDto> createProfile(@RequestBody UserProfileRequestDto dto,Authentication authentication){
        UserProfileResponseDto responseDto= userProfileService.createProfile(dto,authentication);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping
    public ResponseEntity<UserProfileResponseDto> updateProfile(Authentication authentication, @RequestBody UserProfileRequestDto dto){
        UserProfileResponseDto responseDto=userProfileService.updateProfile(dto,authentication);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<UserProfileResponseDto> getProfile(Authentication authentication){
        UserProfileResponseDto responseDto= userProfileService.getProfile(authentication);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping
    public ResponseEntity<?> deleteProfile(Authentication authentication){
      userProfileService.deleteProfile(authentication);
        return ResponseEntity.noContent().build();

    }
}
