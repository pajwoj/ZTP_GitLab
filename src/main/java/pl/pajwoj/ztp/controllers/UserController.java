package pl.pajwoj.ztp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pajwoj.ztp.services.UserService;

@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "User already exists / empty fields.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "200",
                    description = "User registered successfully.",
                    content = @Content
            )
    })
    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Parameter(description = "Details of the user trying to register.") @RequestParam String email, String password) {
        return userService.register(email, password);
    }

    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "User does not exist / empty fields.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "200",
                    description = "User deleted successfully.",
                    content = @Content
            )
    })
    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> delete(@Parameter(description = "E-mail of the user to be removed") @RequestParam String email) {
        return userService.delete(email);
    }
}
