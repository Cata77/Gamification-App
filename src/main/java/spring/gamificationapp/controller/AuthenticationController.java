package spring.gamificationapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.gamificationapp.dto.AuthenticatedUserDto;
import spring.gamificationapp.exception.IncorrectCredentialsException;
import spring.gamificationapp.exception.UserAlreadyTakenException;
import spring.gamificationapp.model.User;
import spring.gamificationapp.service.AuthenticationService;

@RestController
@RequestMapping("/v1/users")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @Operation(
            tags = {"Authentication"},
            description = "This endpoint registers a new user. If the user choose a username that" +
                    " already exists, an error will occur with a suggestive message.",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "The user is registered successfully",
                            content = @Content(schema = @Schema(implementation = AuthenticatedUserDto.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                 "userName": "testUser",
                                                 "password": "test123"
                                             }
                                            """))),
                    @ApiResponse(responseCode = "404",
                            description = "The user already exists",
                            content = @Content(schema = @Schema(implementation = UserAlreadyTakenException.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "message": "User already taken!"
                                            }
                                            """)))
            }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthenticatedUserDto> registerUser(@RequestBody AuthenticatedUserDto user) {
        authenticationService.registerUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }

    @Operation(
            tags = {"Authentication"},
            description = "This endpoint authenticates a user. If the user logs in with wrong credentials" +
                    " , an error will occur with a suggestive message.",
            responses = {@ApiResponse(responseCode = "200",
                    description = "The user is logged in successfully",
                    content = @Content(schema = @Schema(implementation = AuthenticatedUserDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                        Logged in! Welcome testUser!
                                    }
                                    """))),
                    @ApiResponse(responseCode = "404",
                            description = "The credentials are incorrect",
                            content = @Content(schema = @Schema(implementation = IncorrectCredentialsException.class),
                                    examples = @ExampleObject(value = """
                                            {
                                                "message": "Incorrect credentials!"
                                            }
                                            """)))}
    )
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthenticatedUserDto authenticatedUserDto, HttpSession session) {
        User user = authenticationService.loginUser(authenticatedUserDto);

        session.setAttribute("user", user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Logged in! Welcome " + user.getUserName() + "!");
    }
}
