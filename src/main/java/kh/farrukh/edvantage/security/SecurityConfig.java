package kh.farrukh.edvantage.security;

import kh.farrukh.edvantage.security.filters.CustomJWTAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static kh.farrukh.edvantage.security.utils.CustomDslForAuthenticationFilter.customDslForAuthenticationFilter;
import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disabling the CSRF and making the session stateless.
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Allowing all the users to access the register, login and refresh token endpoints.
        http.authorizeRequests().antMatchers(
//                withChildEndpoints(ENDPOINT_REGISTRATION),
                withChildEndpoints(ENDPOINT_LOGIN)
//                withChildEndpoints(ENDPOINT_REFRESH_TOKEN)
        ).permitAll();

        //TODO set endpoint security

        // Adding the custom DSL for the authentication manager and the custom JWT authorization filter.
        http.apply(customDslForAuthenticationFilter());
        http.addFilterBefore(new CustomJWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        // TODO: 7/14/22 after successful login send tokens somewhere: redirect header, success page after login or ...
        // TODO: 7/14/22 for signed in user: send Authorization header in every request
        http.formLogin().defaultSuccessUrl(ENDPOINT_COURSE);

        return http.build();
    }
}