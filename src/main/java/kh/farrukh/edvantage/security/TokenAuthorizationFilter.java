package kh.farrukh.edvantage.security;

import io.jsonwebtoken.JwtException;
import kh.farrukh.edvantage.endpoints.user.AppUser;
import kh.farrukh.edvantage.endpoints.user.UserRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.BadRequestException;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import kh.farrukh.edvantage.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_LOGIN;

@Component
@RequiredArgsConstructor
public class TokenAuthorizationFilter extends OncePerRequestFilter {

    public static final String TOKEN_COOKIE = "access_token";

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().equals(ENDPOINT_LOGIN);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(TOKEN_COOKIE))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new BadRequestException("Token"));

        try {
            String email = tokenService.verifyToken(token);

            AppUser user = userRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("User", "email", email)
            );

            request.setAttribute("login", user);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), null, user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            e.printStackTrace();

            SecurityContextHolder.clearContext();
            ModelAndView mav = new ModelAndView("login");
            mav.addObject("return_url", request.getRequestURI());

            throw new ModelAndViewDefiningException(mav);
        }
    }
}
