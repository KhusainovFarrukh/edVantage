package kh.farrukh.edvantage.security.utils;

import kh.farrukh.edvantage.security.filters.CustomUsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class CustomDslForAuthenticationFilter extends AbstractHttpConfigurer<CustomDslForAuthenticationFilter, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        CustomUsernamePasswordAuthenticationFilter authenticationFilter = new CustomUsernamePasswordAuthenticationFilter(authenticationManager);
        http.addFilter(authenticationFilter);
    }

    public static CustomDslForAuthenticationFilter customDslForAuthenticationFilter() {
        return new CustomDslForAuthenticationFilter();
    }
}
