package food_delivery_app_backend.Exeption;

public class IllegalArgumentException extends RuntimeException{
    public IllegalArgumentException(String messsage){
        super(messsage);
    }
}
