package food_delivery_app_backend.Exeption;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message){
        super(message);
    }
}
