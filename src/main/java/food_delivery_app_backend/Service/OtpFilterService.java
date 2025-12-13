package food_delivery_app_backend.Service;

import food_delivery_app_backend.Exeption.IllegalArgumentException;
import food_delivery_app_backend.Exeption.SomethingIsWrongException;
import org.springframework.stereotype.Service;

@Service
public class OtpFilterService {

    public void otpFilter(String storeOtp, String otp, Long otpExpire) {

        // 1. OTP not generated yet
        if (storeOtp == null || otpExpire == null) {
            throw new SomethingIsWrongException("No OTP generated. Please request a new OTP.");
        }

        // 2. Expired OTP check (always check before equality)
        if (otpExpire < System.currentTimeMillis()) {
            throw new SomethingIsWrongException("OTP has expired. Please request a new one.");
        }

        // 3. Null or empty user input
        if (otp == null || otp.trim().isEmpty()) {
            throw new IllegalArgumentException("OTP cannot be empty.");
        }

        // 4. Wrong OTP
        if (!storeOtp.equals(otp)) {
            throw new IllegalArgumentException("Incorrect OTP. Please try again.");
        }
    }
}

