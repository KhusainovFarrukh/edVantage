package kh.farrukh.edvantage.endpoints.auth;

import kh.farrukh.edvantage.endpoints.user.AppUser;
import kh.farrukh.edvantage.endpoints.user.UserRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.ResourceNotFoundException;
import kh.farrukh.edvantage.jwt.CurrentUser;
import kh.farrukh.edvantage.jwt.TokenBody;
import kh.farrukh.edvantage.jwt.TokenInterceptor;
import kh.farrukh.edvantage.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @GetMapping("login")
    public String login(@RequestParam String email, HttpServletResponse response) {

        AppUser user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "email", email)
        );

        Cookie cookie = new Cookie(
                TokenInterceptor.TOKEN_COOKIE,
                tokenService.createToken(
                        new TokenBody(
                                email,
                                user.getRole()
                                        .getUserFeatures()
                                        .stream().map(Enum::name)
                                        .collect(Collectors.toList())
                        )
                )
        );

        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);

        response.addCookie(cookie);

        return "redirect:/test1";
    }

    @GetMapping("logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(TokenInterceptor.TOKEN_COOKIE, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return "redirect:/login";
    }

    @GetMapping("test1")
    public void test1Page(@CurrentUser Long id, @CurrentUser String name) {
        log.info("User id : {}", id);
        log.info("User name : {}", name);
    }

    @GetMapping("test2")
    public void test2Page(@CurrentUser Long id, @CurrentUser String name) {
        log.info("User id : {}", id);
        log.info("User name : {}", name);
    }
}
