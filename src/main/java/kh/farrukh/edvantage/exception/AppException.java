package kh.farrukh.edvantage.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * It's a RuntimeException that contains the HTTP status code, a message ID, and message arguments
 * Used as base class for handled exceptions and for exception message inter-localization (i18n)
 */
@Getter
@Setter
public class AppException extends RuntimeException {

    private HttpStatus httpStatus;
    private String messageId;
    private Object[] messageArgs;

    public AppException(String message, Throwable cause, HttpStatus httpStatus, String messageId, Object[] messageArgs) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.messageId = messageId;
        this.messageArgs = messageArgs;
    }

    public AppException(String message, HttpStatus httpStatus, String messageId, Object[] messageArgs) {
        super(message);
        this.httpStatus = httpStatus;
        this.messageId = messageId;
        this.messageArgs = messageArgs;
    }
}
