package spring.gamificationapp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.gamificationapp.dto.AuthenticatedUserDto;
import spring.gamificationapp.model.User;
import spring.gamificationapp.service.AuthenticationService;

@RestController
@RequestMapping("/v1/users")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticatedUserDto> registerUser(@RequestBody AuthenticatedUserDto user) {
        authenticationService.registerUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthenticatedUserDto authenticatedUserDto, HttpSession session) {
        User user = authenticationService.loginUser(authenticatedUserDto);

        session.setAttribute("user", user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Logged in! Welcome " + user.getUserName() + "!");
    }
}
