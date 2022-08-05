package kh.farrukh.edvantage.security;

import kh.farrukh.edvantage.endpoints.user.UserRepository;
import kh.farrukh.edvantage.jwt.TokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class AuthenticationFilterConfigurer extends AbstractHttpConfigurer<AuthenticationFilterConfigurer, HttpSecurity> {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final HandlerExceptionResolver resolver;

    public AuthenticationFilterConfigurer(
            UserRepository userRepository,
            TokenService tokenService,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver
    ) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.resolver = resolver;
    }

    @Override
    public void configure(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        EmailPasswordAuthenticationFilter authenticationFilter = new EmailPasswordAuthenticationFilter(
                authenticationManager, userRepository, tokenService, resolver
        );
        http.addFilter(authenticationFilter);
    }
}