package kh.farrukh.edvantage.jwt;

import kh.farrukh.edvantage.endpoints.role.Role;
import kh.farrukh.edvantage.endpoints.user.AppUser;
import kh.farrukh.edvantage.endpoints.user.UserRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter param) {
        return param.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter param,
            ModelAndViewContainer mvc,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory dataBinderFactory
    ) {
        final Map<String, Object> resolved = new HashMap<>();

        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();

        Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(TokenInterceptor.TOKEN_COOKIE))
                .map(Cookie::getValue)
                .findFirst()
                .ifPresent(token -> {
                    TokenBody tokenBody = tokenService.verifyToken(token);
                    AppUser user = userRepository.findByEmail(tokenBody.getEmail()).orElseThrow(
                            () -> new BadRequestException("Email (token)")
                    );

                    // @LoginUser Long id
                    if (param.getParameterType().isAssignableFrom(Long.class)) {
                        resolved.put("value", user.getId());
                    }
                    // @LoginUser String name
                    else if (param.getParameterType().isAssignableFrom(String.class)) {
                        resolved.put("value", user.getName());
                    }
                    // @LoginUser Role role
                    else if (param.getParameterType().isAssignableFrom(Role.class)) {
                        resolved.put("value", user.getRole());
                    }
                    // @LoginUser AppUser user
                    else if (param.getParameterType().isAssignableFrom(AppUser.class)) {
                        resolved.put("value", user);
                    }
                });

        return resolved.get("value");
    }
}
