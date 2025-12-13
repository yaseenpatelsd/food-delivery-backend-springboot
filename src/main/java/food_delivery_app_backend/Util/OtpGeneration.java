package food_delivery_app_backend.Util;

import org.springframework.stereotype.Component;

@Component
public class OtpGeneration {

    public String generateOtp(){
        int otp= 100000+(int)(Math.random()*900000);
        return String.valueOf(otp);
    }

    public long otpExpireDate(long minutes){
        return System.currentTimeMillis()+(minutes*60*1000);
    }
}
