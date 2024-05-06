package pl.pajwoj.ztp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.pajwoj.ztp.services.UserService;

@RestController
@RequestMapping(path = "api/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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

    @Operation(summary = "Authenticate an user (login)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "User is already logged in or user not found.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "User provided wrong password.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "200",
                    description = "User authenticated successfully.",
                    content = @Content
            )
    })
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestParam @AuthenticationPrincipal @Parameter(description = "E-mail of the user to be logged in") String email, @RequestParam @Parameter(description = "Password of the user to be logged in") String password, HttpServletRequest req, HttpServletResponse res) {
        return userService.login(email, password, req, res);
    }

    @Operation(summary = "Log out (destroy session for an user, unauthenticate)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "User is not logged in.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "200",
                    description = "User logged out successfully.",
                    content = @Content
            )
    })
    @PostMapping(path = "/logout")
    public ResponseEntity<?> logoutPOST(HttpServletRequest req, HttpServletResponse res) {
        return userService.logout(req, res);
    }

    @Operation(summary = "Log out (destroy session for an user, unauthenticate)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "400",
                    description = "User is not logged in.",
                    content = @Content
            ),

            @ApiResponse(
                    responseCode = "200",
                    description = "User logged out successfully.",
                    content = @Content
            )
    })
    @GetMapping(path = "/logout")
    public ResponseEntity<?> logoutGET(HttpServletRequest req, HttpServletResponse res) {
        return userService.logout(req, res);
    }

    @GetMapping(path = "/current")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest req) {
        return userService.getCurrentUser(req);
    }
}
