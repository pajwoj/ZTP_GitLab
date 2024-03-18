package pl.pajwoj.ztp.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pajwoj.ztp.models.User;
import pl.pajwoj.ztp.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> register(String email, String password) {
        if(email.isBlank() || password.isBlank()) return new ResponseEntity<>("Email and password can't be empty!", HttpStatus.BAD_REQUEST);
        if(userRepository.existsByEmail(email)) return new ResponseEntity<>("User with email: " + email + " already exists!", HttpStatus.BAD_REQUEST);

        userRepository.save(new User(
                email,
                encoder.encode(password)
        ));

        return new ResponseEntity<>("Registration successful!", HttpStatus.OK);
    }

    public ResponseEntity<?> delete(String email) {
        if(email.isBlank()) return new ResponseEntity<>("Details can't be empty!", HttpStatus.BAD_REQUEST);
        if(userRepository.findByEmail(email).isEmpty()) return new ResponseEntity<>("User not found in database!", HttpStatus.BAD_REQUEST);

        userRepository.delete(userRepository.findByEmail(email).get());
        return new ResponseEntity<>("User succesfully deleted!", HttpStatus.OK);
    }
}
