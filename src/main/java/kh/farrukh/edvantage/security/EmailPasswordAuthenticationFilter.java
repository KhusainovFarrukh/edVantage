package kh.farrukh.edvantage.security;

import kh.farrukh.edvantage.endpoints.user.AppUser;
import kh.farrukh.edvantage.endpoints.user.UserRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import kh.farrukh.edvantage.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kh.farrukh.edvantage.security.TokenAuthorizationFilter.TOKEN_COOKIE;
import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_COURSE;

@RequiredArgsConstructor
public class EmailPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication
    ) throws IOException {
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
    }
}
