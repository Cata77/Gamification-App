package spring.gamificationapp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(UserAlreadyTakenException.class)
    public ResponseEntity<ErrorDetails> exceptionUserAlreadyTakenHandler() {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage("User already taken!");
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity<ErrorDetails> exceptionIncorrectCredentialsHandler() {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage("Incorrect credentials!");
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }
}
