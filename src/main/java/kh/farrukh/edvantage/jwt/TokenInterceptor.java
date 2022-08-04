package kh.farrukh.edvantage.jwt;

import io.jsonwebtoken.JwtException;
import kh.farrukh.edvantage.endpoints.user.AppUser;
import kh.farrukh.edvantage.endpoints.user.UserRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.BadRequestException;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    public static final String TOKEN_COOKIE = "access_token";

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws ModelAndViewDefiningException {

        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(TokenInterceptor.TOKEN_COOKIE))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new BadRequestException("Token"));

        try {
            TokenBody tokenBody = tokenService.verifyToken(token);

            AppUser user = userRepository.findByEmail(tokenBody.getEmail()).orElseThrow(
                    () -> new ResourceNotFoundException("User", "email", tokenBody.getEmail())
            );

            request.setAttribute("login", user);
        } catch (JwtException e) {
            e.printStackTrace();

            ModelAndView mav = new ModelAndView("login");
            mav.addObject("return_url", request.getRequestURI());

            throw new ModelAndViewDefiningException(mav);
        }

        return true;
    }
}