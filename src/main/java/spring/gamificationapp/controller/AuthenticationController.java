package spring.gamificationapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.gamificationapp.dto.AuthenticatedUserDto;
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
}
