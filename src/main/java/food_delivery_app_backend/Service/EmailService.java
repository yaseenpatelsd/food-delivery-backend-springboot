package food_delivery_app_backend.Service;

import food_delivery_app_backend.Entity.Order.OrderEntity;
import food_delivery_app_backend.Entity.UserEntities.DeliveryPartnerEntity;
import food_delivery_app_backend.Entity.UserEntities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.SocketImpl;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public String otpVerifiacation(String email,String otp){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Otp verification for account registration");
        simpleMailMessage.setText("Otp for account verification"+ "\n"+otp +"\n"+"Do not share it with anyone its one time otp and will expire in 10 minutes thanks for choosing our service we work to provice best service to our customers" );

        javaMailSender.send(simpleMailMessage);

        return "email is sanded to"+email;
    }

    public String otpRequest(String email,String otp){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("otp for password reset");
        simpleMailMessage.setText("Otp for password reset"+ "\n"+otp +"\n"+"Do not share it with anyone its one time otp and will expire in 10 minutes thanks for choosing our service we work to provice best service to our customers" );

        javaMailSender.send(simpleMailMessage);

        return "email is sanded to"+email;
    }

    public String deliveryPartnerCencelOrder(String email,OrderEntity order){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Delivery Partner Update");
        simpleMailMessage.setText("Hi "+ order.getRestaurant().getOwner().getEmail() +","+"\n"+

        "Just a quick update — the delivery partner assigned to this order wasn’t able to complete the pickup and has canceled the order. "+"\n"+

                "Please assign another delivery partner so the order can go out as soon as possible." +"\n"+

        "Thanks for your cooperation,"+ "\n"+
       "Food Delivery" + "\n"+"Merchant Support"
    );

        javaMailSender.send(simpleMailMessage);

        return "email is sanded to"+email;
    }
  public String deliveryConfrimOtp(String email, String otp, UserEntity user,DeliveryPartnerEntity dp){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Subject: Your Order Is Arriving in 10 Minutes!");
        simpleMailMessage.setText(" Hi " +user.getUserPersonalProfileEntity().getFullname()+"\n"+
                otp +"\n"+

      "Good news—your food is on the way! 🚚"+"\n"+
      "Your order will be delivered in approximately 10 minutes."+"\n"+

              "Your delivery partner is" +dp.getDeliveryPersonalProfileEntity().getFullname() +", and they’ll be arriving on a bike with the number" +dp.getVehicleNo()+"."+"\n"+

      "If you need any help, feel free to reach out."+"\n"+
      "Enjoy your meal!"+"\n"+

              "Best regards,"+"\n"+
"Food Delivery" );

        javaMailSender.send(simpleMailMessage);

        return "email is sanded to"+email;
    }

    public String emailToConfirmPickUp(String email, String otp, DeliveryPartnerEntity deliveryPartner, OrderEntity order){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Subject: Delivery Assignment and Address Confirmation");
        simpleMailMessage.setText("Hello "+deliveryPartner.getDeliveryPersonalProfileEntity().getFullname() +"\n" +
                "Please pick up and provide this code to pick up"+"\n" + otp+"\n"+"deliver the order to the following address: "+"\n"+
                order.getUser().getUserPersonalProfileEntity().getAddress()+" "+order.getUser().getUserPersonalProfileEntity().getCity()
                +" "+order.getUser().getUserPersonalProfileEntity().getState()+" "+order.getUser().getUserPersonalProfileEntity().getCountry() +"\n"
                +
        "Make sure to handle the package with care and deliver it promptly. If you have any issues or need assistance, let us know."+"\n"+

        "Thank you!);");

        javaMailSender.send(simpleMailMessage);

        return "email is sanded to"+email;
    }
}
