package pl.pajwoj.ztp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import pl.pajwoj.ztp.repositories.UserRepository;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.COOKIES;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public SecurityConfig(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new MyAuthenticationProvider(encoder, userRepository);
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(COOKIES));
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .authenticationProvider(authenticationProvider())

                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                )

                .authorizeHttpRequests(auth -> auth
                        .anyRequest()
                        .permitAll()
                );

        return http.build();
    }
}
