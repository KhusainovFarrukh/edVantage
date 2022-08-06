package kh.farrukh.edvantage.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {
    // TODO: 8/4/22 currently returning json (change ApiExceptionHandler), needs /custom-error page and
    //  overriding default /error page

    private final MessageSource messageSource;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException exception, Locale locale) {
        exception.printStackTrace();
        return new ResponseEntity<>(
                new ErrorResponse(
                        messageSource.getMessage(
                                exception.getMessageId(),
                                exception.getMessageArgs(),
                                locale
                        ),
                        exception.getHttpStatus(),
                        exception.getHttpStatus().value(),
                        ZonedDateTime.now()
                ),
                exception.getHttpStatus()
        );
    }

    // TODO: 8/6/22 override default /error page instead of this
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleUnknownException(Exception exception, Locale locale) {
//        exception.printStackTrace();
//        return new ResponseEntity<>(
//                new ErrorResponse(
//                        messageSource.getMessage(
//                                EXCEPTION_UNKNOWN,
//                                null,
//                                locale
//                        ) + ": " + exception.getMessage(),
//                        HttpStatus.INTERNAL_SERVER_ERROR,
//                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                        ZonedDateTime.now()
//                ),
//                HttpStatus.INTERNAL_SERVER_ERROR
//        );
//    }
}
