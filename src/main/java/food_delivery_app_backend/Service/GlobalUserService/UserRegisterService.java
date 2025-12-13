package food_delivery_app_backend.Service.GlobalUserService;

import food_delivery_app_backend.Dto.UserRegisterFlow.AuthResponseDto;
import food_delivery_app_backend.Entity.UserEntities.GlobalUserEntity;
import food_delivery_app_backend.Entity.UserEntities.UserEntity;
import food_delivery_app_backend.Exeption.IllegalArgumentException;
import food_delivery_app_backend.Exeption.UserAlreadyExistException;
import food_delivery_app_backend.Exeption.UserNotFoundException;
import food_delivery_app_backend.Jwt.JwtUtil;
import food_delivery_app_backend.Repository.Global.GlobalUserRepository;
import food_delivery_app_backend.Repository.Global.UserRepository;
import food_delivery_app_backend.Service.EmailService;
import food_delivery_app_backend.Service.OtpFilterService;
import food_delivery_app_backend.Util.OtpGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRegisterService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OtpGeneration otpGeneration;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private OtpFilterService otpFilterService;
    @Autowired
    private GlobalUserRepository globalUserRepository;

    public String registerUser(GlobalUserEntity user,String username,String password,String email){

        Optional<GlobalUserEntity> globalUserEntityOptional= globalUserRepository.findByUsername(username);

        if (globalUserEntityOptional.isPresent()){
          throw new UserAlreadyExistException("This username is already register");
        }

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setIsActive(false);


        //generate otp and expire date
        String otp= otpGeneration.generateOtp();
        long expireTime=otpGeneration.otpExpireDate(10);

        //Otp sanding
        emailService.otpVerifiacation(email, otp);

        user.setOtp(otp);
       user.setOtpExpire(expireTime);

       globalUserRepository.save(user);
        return "Account register successfully please very email before log in";

    }


    public void accountVerification(String username,String otp){
        GlobalUserEntity globalUser= findUser(username);
        String storeOtp=globalUser.getOtp();
        Long expireTime=globalUser.getOtpExpire();

       otpFilterService.otpFilter(storeOtp,otp,expireTime);

        globalUser.setOtp(null);
        globalUser.setOtpExpire(null);
        globalUser.setIsActive(true);

        globalUserRepository.save(globalUser);
    }

    public void otpRequest(String username){
        GlobalUserEntity globalUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Username not found"));

        String otp = otpGeneration.generateOtp();
        Long otpExpire = otpGeneration.otpExpireDate(10);

        emailService.otpRequest(globalUser.getEmail(), otp);

        globalUser.setOtp(otp);
        globalUser.setOtpExpire(otpExpire);

        globalUserRepository.save(globalUser);
    }

   public void passwordReset(String username,String otp,String password){
        GlobalUserEntity user= findUser(username);
       String storeOtp=user.getOtp();
       Long otpExpire=user.getOtpExpire();

       otpFilterService.otpFilter(storeOtp,otp,otpExpire);

       if (user.getPassword().equals(password)){
           throw new IllegalArgumentException("same password as before");
       }

       user.setOtp(null);
       user.setOtpExpire(null);
       user.setPassword(passwordEncoder.encode(password));
       globalUserRepository.save(user);

   }

   public AuthResponseDto login(String username,String password){
       GlobalUserEntity user= findUser(username);

       if (!user.getIsActive()){
           throw new IllegalArgumentException("Account is not verified please verified it first");
       }
       try {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(username, password)
           );
       }catch (Exception e){
           throw new RuntimeException(e.getMessage());
       }

       String token= jwtUtil.generateToken(username);
       return new AuthResponseDto(token);
   }

/*--------------------------------------------helper methods------------------------------------------------------------------*/

public GlobalUserEntity findUser(String username){
    return globalUserRepository.findByUsername(username)
            .orElseThrow(()-> new UserNotFoundException("Account is not found by this username "));
}
}
