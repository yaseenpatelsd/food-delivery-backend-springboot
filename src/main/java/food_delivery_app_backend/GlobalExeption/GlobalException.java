package food_delivery_app_backend.GlobalExeption;

import food_delivery_app_backend.Dto.ResponseDto;
import food_delivery_app_backend.Exeption.*;
import food_delivery_app_backend.Exeption.IllegalArgumentException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(ResouceNotFoundException.class)
    public ResponseEntity<ResponseDto> jsonerror(ResouceNotFoundException ex,HttpServletRequest request){
        return build(HttpStatus.BAD_REQUEST,"Resource not avaialable ", ex.getMessage(), request);
    }
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ResponseDto> jsonerror(ResourceAlreadyExistException ex,HttpServletRequest request){
        return build(HttpStatus.ALREADY_REPORTED,"Resource already available ", ex.getMessage(), request);
    }
    @ExceptionHandler(SomethingIsWrongException.class)
    public ResponseEntity<ResponseDto> jsonerror(SomethingIsWrongException ex,HttpServletRequest request){
        return build(HttpStatus.INTERNAL_SERVER_ERROR,"Something is wrong ", ex.getMessage(), request);
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ResponseDto> jsonerror(UserAlreadyExistException ex, HttpServletRequest request){
        return build(HttpStatus.ALREADY_REPORTED,"Username is already register ", ex.getMessage(), request);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDto> jsonerror(UserNotFoundException ex,HttpServletRequest request){
        return build(HttpStatus.BAD_REQUEST,"username does not exist ", ex.getMessage(), request);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> jsonerror(IllegalArgumentException ex,HttpServletRequest request){
        return build(HttpStatus.UNAUTHORIZED,"user not allowed to do this activity ", ex.getMessage(), request);
    }
    @ExceptionHandler(RazorPayException.class)
    public ResponseEntity<ResponseDto> jsonEroor(RazorPayException rs, HttpServletRequest request){
        return build(HttpStatus.BAD_REQUEST,"Error with the razorpay", rs.getMessage(), request);
    }

    public ResponseEntity<ResponseDto> build(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request
    ){
        ResponseDto responseDto=new ResponseDto(
                LocalDateTime.now(),
                status.value(),
                error,
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(responseDto);
    }
}
