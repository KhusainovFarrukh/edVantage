package kh.farrukh.edvantage.exception;

import kh.farrukh.edvantage.endpoints.user.UserRepository;
import kh.farrukh.edvantage.exception.custom_exceptions.WrongEmailPasswordException;
import kh.farrukh.edvantage.utils.constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class AppExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(AppException.class)
    public ModelAndView handleApiException(AppException exception, Locale locale) {
        exception.printStackTrace();
        ModelAndView errorPage = new ModelAndView("error");

        String message = messageSource.getMessage(exception.getMessageId(), exception.getMessageArgs(), locale);

        errorPage.addObject("code", exception.getHttpStatus().value());
        errorPage.addObject("message", message);

        // TODO: 8/7/22 returning empty page for security related custom exceptions
        //  (WrongEmailPasswordException)
        return errorPage;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(
            AccessDeniedException exception,
            Locale locale
    ) {
        exception.printStackTrace();
        ModelAndView errorPage = new ModelAndView("error");

        String message = messageSource.getMessage(ExceptionMessages.EXCEPTION_NOT_ENOUGH_PERMISSION, null, locale);

        errorPage.addObject("code", 403);
        errorPage.addObject("message", message);

        return errorPage;
    }
}
