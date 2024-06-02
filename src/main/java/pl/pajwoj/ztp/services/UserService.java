package pl.pajwoj.ztp.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pajwoj.ztp.models.User;
import pl.pajwoj.ztp.repositories.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    public UserService(UserRepository userRepository, PasswordEncoder encoder, AuthenticationProvider authenticationProvider, LogoutHandler logoutHandler) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authenticationProvider = authenticationProvider;
        this.logoutHandler = logoutHandler;
    }

    /**
     * @param email E-mail of the user to be registered
     * @param password Password of the user to be registered
     * @return Server response
     */
    public ResponseEntity<String> register(String email, String password) {
        if(email.isBlank() || password.isBlank()) return new ResponseEntity<>("Email and password can't be empty!", HttpStatus.BAD_REQUEST);
        if(userRepository.existsByEmail(email)) return new ResponseEntity<>("User with email: " + email + " already exists!", HttpStatus.BAD_REQUEST);

        userRepository.save(new User(
                email,
                encoder.encode(password)
        ));

        return new ResponseEntity<>("Registration successful!", HttpStatus.OK);
    }

    /**
     * @param email E-mail of the user to be deleted
     * @return Server response
     */
    public ResponseEntity<?> delete(String email) {
        if(email.isBlank()) return new ResponseEntity<>("Details can't be empty!", HttpStatus.BAD_REQUEST);
        if(userRepository.findByEmail(email).isEmpty()) return new ResponseEntity<>("User not found in database!", HttpStatus.BAD_REQUEST);

        userRepository.delete(userRepository.findByEmail(email).get());
        return new ResponseEntity<>("User succesfully deleted!", HttpStatus.OK);
    }

    /**
     * @param email E-mail of the user to be logged in
     * @param password Password of the user to be logged in
     * @param req Request object
     * @param res Response object
     * @return Server response
     */
    public ResponseEntity<?> login(String email, String password, HttpServletRequest req, HttpServletResponse res) {
        Optional<User> u = userRepository.findByEmail(email);
        SecurityContext context = SecurityContextHolder.getContext();
        HttpSession session = req.getSession(true);

        if(context.getAuthentication() instanceof UsernamePasswordAuthenticationToken)
            return new ResponseEntity<>("Already logged in!", HttpStatus.BAD_REQUEST);

        if(u.isEmpty())
            return new ResponseEntity<>("User not found in database!", HttpStatus.BAD_REQUEST);

        if(!encoder.matches(password, u.get().getPassword()))
            return new ResponseEntity<>("Wrong password!", HttpStatus.UNAUTHORIZED);

        UsernamePasswordAuthenticationToken unauthenticatedToken = UsernamePasswordAuthenticationToken.unauthenticated(email, password);
        UsernamePasswordAuthenticationToken authenticatedToken = (UsernamePasswordAuthenticationToken) authenticationProvider.authenticate(unauthenticatedToken);

        context.setAuthentication(authenticatedToken);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);

        return new ResponseEntity<>("Successfully logged in!", HttpStatus.OK);
    }

    /**
     * @param req Request object
     * @param res Response object
     * @return Server response
     */
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res) {
        SecurityContext context = SecurityContextHolder.getContext();
        HttpSession session = req.getSession(false);

        if(context.getAuthentication() instanceof AnonymousAuthenticationToken)
            return new ResponseEntity<>("Not logged in!", HttpStatus.BAD_REQUEST);

        logoutHandler.logout(req, res, context.getAuthentication());
        session.invalidate();

        return new ResponseEntity<>("Successfully logged out!", HttpStatus.OK);
    }

    /**
     * @param req Request object
     * @return Server response, if OK contains the e-mail of the user associated with the session
     */
    public ResponseEntity<?> getCurrentUser(HttpServletRequest req) {
        SecurityContext context = SecurityContextHolder.getContext();

        if(context.getAuthentication() instanceof AnonymousAuthenticationToken)
            return new ResponseEntity<>("Not logged in!", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(context.getAuthentication().getPrincipal().toString(), HttpStatus.OK);
    }
}
