package kh.farrukh.edvantage.exception.custom_exceptions;

import kh.farrukh.edvantage.exception.AppException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static kh.farrukh.edvantage.utils.constants.ExceptionMessages.EXCEPTION_NOT_ENOUGH_PERMISSION;

@Getter
public class NotEnoughPermissionException extends AppException {

    public NotEnoughPermissionException() {
        super(
                "You don't have enough permission",
                HttpStatus.FORBIDDEN,
                EXCEPTION_NOT_ENOUGH_PERMISSION,
                new Object[]{}
        );
    }
}