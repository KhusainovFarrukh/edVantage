package kh.farrukh.edvantage.security;

import kh.farrukh.edvantage.endpoints.user.UserRepository;
import kh.farrukh.edvantage.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFilterConfigurer extends AbstractHttpConfigurer<AuthenticationFilterConfigurer, HttpSecurity> {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public void configure(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        EmailPasswordAuthenticationFilter authenticationFilter = new EmailPasswordAuthenticationFilter(
                authenticationManager, userRepository, tokenService
        );
        http.addFilter(authenticationFilter);
    }
}