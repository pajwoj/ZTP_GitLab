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

    private void addUser(String email, String password) {
        userRepository.save(new User(
                email,
                encoder.encode(password)
        ));
    }

    public ResponseEntity<String> register(String email, String password) {
        if(userRepository.existsByEmail(email)) return new ResponseEntity<>("User with email: " + email + " already exists!", HttpStatus.BAD_REQUEST);

        this.addUser(email, password);
        return new ResponseEntity<>("Registration successful!", HttpStatus.OK);
    }
}
