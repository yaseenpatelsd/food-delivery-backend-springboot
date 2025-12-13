package food_delivery_app_backend.Exeption;

public class SomethingIsWrongException extends RuntimeException{
    public SomethingIsWrongException(String message){
        super(message);
    }
}
