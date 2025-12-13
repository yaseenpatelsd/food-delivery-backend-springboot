package food_delivery_app_backend.Exeption;

public class ResourceAlreadyExistException extends RuntimeException{
    public ResourceAlreadyExistException(String message){
        super(message);
    }
}
