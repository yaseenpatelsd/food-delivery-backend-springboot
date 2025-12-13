package food_delivery_app_backend.Config;

import com.razorpay.RazorpayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorPayConfig {

    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String secret;


    @Bean
   public RazorpayClient razorpayClient(){
        try {
            return new RazorpayClient(keyId,secret);
        }catch (Exception e){
            throw  new RuntimeException("Fail to create razorpay client "+e.getMessage());
        }
    }

}
