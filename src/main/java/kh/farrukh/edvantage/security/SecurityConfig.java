package kh.farrukh.edvantage.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.Cookie;

import static kh.farrukh.edvantage.security.TokenAuthorizationFilter.TOKEN_COOKIE;
import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_LOGIN;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            TokenAuthorizationFilter tokenAuthorizationFilter,
            AuthenticationFilterConfigurer authenticationFilterConfigurer
    ) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.apply(authenticationFilterConfigurer);
        http.addFilterBefore(tokenAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        // TODO: 8/5/22 needs endpoint for register
        http.formLogin();
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
