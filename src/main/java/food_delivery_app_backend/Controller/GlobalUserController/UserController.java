package food_delivery_app_backend.Controller.GlobalUserController;

import food_delivery_app_backend.Dto.UserRegisterFlow.*;
import food_delivery_app_backend.Entity.UserEntities.UserEntity;
import food_delivery_app_backend.Enum.GlobalRole;
import food_delivery_app_backend.Repository.Global.UserRepository;
import food_delivery_app_backend.Service.GlobalUserService.UserRegisterService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserRegisterService userRegisterService;

    public UserController(UserRepository userRepository, UserRegisterService userRegisterService) {
        this.userRepository = userRepository;
        this.userRegisterService = userRegisterService;
    }

    @PostMapping("/register")
   public ResponseEntity<?> UserRegister(@RequestBody UserRegisterDto userRegisterDto){
       UserEntity appUser=new UserEntity();
       appUser.setRole(GlobalRole.USER);

       userRegisterService.registerUser(appUser, userRegisterDto.getUsername(), userRegisterDto.getPassword(), userRegisterDto.getEmail());
       return ResponseEntity.ok("Successfully register please verify before login");
   }

    @PostMapping("/account-verification")
    public ResponseEntity<?> accountVerification(@RequestBody AccountVerificationDto accountVerificationDto){
           userRegisterService.accountVerification(accountVerificationDto.getUsername(),accountVerificationDto.getOtp());
           return ResponseEntity.ok("Account verified Successfully you can log in now");
    }

    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOtp(@RequestBody OtpRequestDto otpRequestDto) {
       userRegisterService.otpRequest(otpRequestDto.getUsername());
        return ResponseEntity.ok("email is sanded to your email");
    }

    @PostMapping("/password-reset")
    public ResponseEntity<?> passwordReset(@RequestBody PasswordResetDto passwordResetDto){
          userRegisterService.passwordReset(passwordResetDto.getUsername(), passwordResetDto.getOtp(), passwordResetDto.getPassword());
            return ResponseEntity.status(200).body("New password is saved");
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody UserLoginDto userLoginDto) {
      AuthResponseDto authResponseDto= userRegisterService.login(userLoginDto.getUsername(), userLoginDto.getPassword());
      return ResponseEntity.ok(authResponseDto);
    }



}
