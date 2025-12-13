package food_delivery_app_backend.Exeption;

public class RazorPayException extends RuntimeException{
    public RazorPayException(String message){
        super(message);
    }
}
