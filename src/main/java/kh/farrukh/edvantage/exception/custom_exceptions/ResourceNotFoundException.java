package kh.farrukh.edvantage.exception.custom_exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kh.farrukh.edvantage.exception.AppException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static kh.farrukh.edvantage.utils.constants.ExceptionMessages.EXCEPTION_RESOURCE_NOT_FOUND;

/**
 * It's a custom exception class that extends the ApiException class
 * and is used to throw a ResourceNotFoundException when a resource
 * is not found
 * <p>
 * HttpStatus of the response will be NOT_FOUND
 */
@Getter
@JsonIgnoreProperties
public class ResourceNotFoundException extends AppException {

    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(
                String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue),
                HttpStatus.NOT_FOUND,
                EXCEPTION_RESOURCE_NOT_FOUND,
                new Object[]{resourceName, fieldName, fieldValue}
        );
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}