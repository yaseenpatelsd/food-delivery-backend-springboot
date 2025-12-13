package food_delivery_app_backend.Controller.GlobalUserController;

import food_delivery_app_backend.Dto.DeliveryPartnerFlow.*;
import food_delivery_app_backend.Dto.GlobalResponse.GlobalResponseDto;
import food_delivery_app_backend.Dto.GlobalResponse.OwnerResponseDto;
import food_delivery_app_backend.Dto.UserRegisterFlow.AuthResponseDto;
import food_delivery_app_backend.Entity.UserEntities.DeliveryPartnerEntity;
import food_delivery_app_backend.Enum.GlobalRole;
import food_delivery_app_backend.Service.GlobalUserService.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery/partner")
public class DeliveryBoyController {
    @Autowired
    private UserRegisterService userRegisterService;


    @PostMapping("/register")
    public ResponseEntity<?> registerOwner(@RequestBody DeliveryPartnerRegisterDto deliveryPartnerRegisterDto){
        DeliveryPartnerEntity deliveryPartnerEntity=new DeliveryPartnerEntity();
        deliveryPartnerEntity.setVehicleNo(deliveryPartnerRegisterDto.getVehicleNo());
        deliveryPartnerEntity.setOnDuty(deliveryPartnerRegisterDto.getOnDuty());
        deliveryPartnerEntity.setRole(GlobalRole.DELIVERY_PARTNER);
        deliveryPartnerEntity.setOccupied(false);



        userRegisterService.registerUser(deliveryPartnerEntity,deliveryPartnerRegisterDto.getUsername(),
                                         deliveryPartnerRegisterDto.getPassword(), deliveryPartnerRegisterDto.getEmail());



        return ResponseEntity.ok("Register successful please verify your email");
    }

    @PostMapping("/account-verification")
    public ResponseEntity<?> accountVerification(@RequestBody DeliveryPartnerAccountVerificationDto deliveryPartnerAccountVerificationDto){
        userRegisterService.accountVerification(deliveryPartnerAccountVerificationDto.getUsername(), deliveryPartnerAccountVerificationDto.getOtp());
        return ResponseEntity.ok("Owner Account is register now you can run your business on our app");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DeliveryPartnerLoginDto deliveryPartnerLoginDto){
        AuthResponseDto authResponseDto=userRegisterService.login(deliveryPartnerLoginDto.getUsername(),deliveryPartnerLoginDto.getPassword());
        return ResponseEntity.ok(authResponseDto);
    }

    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOtpForPasswordReset(@RequestBody DeliveryPartnerOtpRequestDto otpRequestDto){
        userRegisterService.otpRequest(otpRequestDto.getUsername());
        return ResponseEntity.ok("Otp is sanded to your register email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody DeliveryPartnerPasswordResetDto deliveryPartnerPasswordResetDto){
        userRegisterService.passwordReset(deliveryPartnerPasswordResetDto.getUsername(), deliveryPartnerPasswordResetDto.getOtp(), deliveryPartnerPasswordResetDto.getPassword());

        return ResponseEntity.ok("Password change successfully");
    }

}
