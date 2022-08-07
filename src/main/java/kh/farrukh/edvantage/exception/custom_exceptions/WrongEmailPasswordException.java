package kh.farrukh.edvantage.exception.custom_exceptions;

import kh.farrukh.edvantage.exception.AppException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static kh.farrukh.edvantage.utils.constants.ExceptionMessages.EXCEPTION_WRONG_EMAIL_PASSWORD;

@Getter
public class WrongEmailPasswordException extends AppException {

    private final Type type;

    public WrongEmailPasswordException(Type type) {
        super(
                String.format("%s is wrong", type.getName()),
                HttpStatus.UNAUTHORIZED,
                EXCEPTION_WRONG_EMAIL_PASSWORD,
                new Object[]{type.getName()}
        );
        this.type = type;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Type {
        EMAIL("Email"),
        PASSWORD("Password");

        private final String name;
    }
}
