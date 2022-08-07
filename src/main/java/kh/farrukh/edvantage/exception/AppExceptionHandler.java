package kh.farrukh.edvantage.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

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
        return errorPage;
    }
}
