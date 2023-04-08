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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> exceptionUserNotFoundHandler() {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage("User not found!");
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorDetails> exceptionCategoryNotFoundHandler() {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage("Category not found! Choose one from : Programming,Economy,Geography," +
                "Music,Movies,Football");
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }

    @ExceptionHandler(NotEnoughTokensException.class)
    public ResponseEntity<ErrorDetails> exceptionNotEnoughTokensHandler() {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage("Number of tokens awarded should be between 10 and 50!");
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }

    @ExceptionHandler(NotEnoughOptionsException.class)
    public ResponseEntity<ErrorDetails> exceptionNotEnoughOptionsHandler() {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage("4 options required!");
        return ResponseEntity
                .badRequest()
                .body(errorDetails);
    }
}
