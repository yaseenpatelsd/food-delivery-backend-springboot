package food_delivery_app_backend.Controller.GlobalUserController;

import food_delivery_app_backend.Dto.OwnerRegisterFLow.*;
import food_delivery_app_backend.Dto.UserRegisterFlow.AuthResponseDto;
import food_delivery_app_backend.Entity.UserEntities.OwnerEntity;
import food_delivery_app_backend.Enum.GlobalRole;
import food_delivery_app_backend.Service.GlobalUserService.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private UserRegisterService userRegisterService;


    @PostMapping("/register")
    public ResponseEntity<?> registerOwner(@RequestBody OwnerRegisterDto ownerRegisterDto){
        OwnerEntity ownerEntity=new OwnerEntity();
        ownerEntity.setRole(GlobalRole.OWNER);

        userRegisterService.registerUser(ownerEntity,ownerRegisterDto.getUsername(),
                ownerRegisterDto.getPassword(), ownerRegisterDto.getEmail());

        return ResponseEntity.ok("Register successful please verify your email");
    }

    @PostMapping("/account-verification")
    public ResponseEntity<?> accountVerification(@RequestBody OwnerAccountVerificationDto accountVerificationDto){
        userRegisterService.accountVerification(accountVerificationDto.getUsername(), accountVerificationDto.getOtp());
        return ResponseEntity.ok("Owner Account is register now you can run your business on our app");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody OwnerLoginDto ownerLoginDto){
        AuthResponseDto authResponseDto=userRegisterService.login(ownerLoginDto.getUsername(),ownerLoginDto.getPassword());
        return ResponseEntity.ok(authResponseDto);
    }

    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOtpForPasswordReset(@RequestBody OwnerAcOtpRequestDto otpRequestDto){
        userRegisterService.otpRequest(otpRequestDto.getUsername());
        return ResponseEntity.ok("Otp is sanded to your register email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody OwnerAcPasswordResetDto ownerAcPasswordResetDto){
        userRegisterService.passwordReset(ownerAcPasswordResetDto.getUsername(), ownerAcPasswordResetDto.getOtp(), ownerAcPasswordResetDto.getPassword());

        return ResponseEntity.ok("Password change successfully");
    }

}
