package food_delivery_app_backend.Exeption;

public class ResouceNotFoundException extends RuntimeException{
    public ResouceNotFoundException(String message){
        super(message);
    }
}
