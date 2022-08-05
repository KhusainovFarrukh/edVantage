package kh.farrukh.edvantage.security;

import kh.farrukh.edvantage.endpoints.user.AppUser;
import kh.farrukh.edvantage.endpoints.user.UserRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import kh.farrukh.edvantage.exception.custom_exceptions.WrongEmailPasswordException;
import kh.farrukh.edvantage.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

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
    private final HandlerExceptionResolver resolver;

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

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) {
        // TODO: 8/5/22 don't redirect to error page, show modal (dialog)
        if (userRepository.existsByEmail(request.getParameter("username"))) {
            resolver.resolveException(
                    request, response, null, new WrongEmailPasswordException(WrongEmailPasswordException.Type.PASSWORD)
            );
        } else {
            resolver.resolveException(
                    request, response, null, new WrongEmailPasswordException(WrongEmailPasswordException.Type.EMAIL)
            );
        }
    }
}
