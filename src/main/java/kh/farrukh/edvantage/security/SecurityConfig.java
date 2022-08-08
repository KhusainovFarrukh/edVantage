package kh.farrukh.edvantage.security;

import kh.farrukh.edvantage.endpoints.user.AppUser;
import kh.farrukh.edvantage.endpoints.user.UserRepository;
import kh.farrukh.edvantage.endpoints.user.UserService;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import kh.farrukh.edvantage.jwt.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.Cookie;

import static kh.farrukh.edvantage.security.TokenAuthorizationFilter.TOKEN_COOKIE;
import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_COURSE;
import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_LOGIN;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            TokenAuthorizationFilter tokenAuthorizationFilter,
            UserRepository userRepository,
            TokenService tokenService
    ) throws Exception {
        // TODO: 8/5/22 needs endpoint for register
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(tokenAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        http.formLogin()
                .successHandler((request, response, authentication) -> {
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    AppUser user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                            () -> new ResourceNotFoundException("User", "email", userDetails.getUsername())
                    );
                    Cookie cookie = new Cookie(
                            TOKEN_COOKIE,
                            tokenService.createToken(user.getEmail())
                    );

                    cookie.setPath("/");
                    cookie.setMaxAge(Integer.MAX_VALUE);

                    response.addCookie(cookie);
                    response.sendRedirect(ENDPOINT_COURSE);
                });
        http.logout(logout -> logout.logoutSuccessHandler((request, response, authentication) -> {
                    Cookie cookie = new Cookie(TOKEN_COOKIE, "");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);

                    response.addCookie(cookie);

                    response.sendRedirect(ENDPOINT_LOGIN);
                })
        );

        return http.build();
    }
}
