package kh.farrukh.edvantage.exception;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * Custom POJO for returning as JSON response when an exception is thrown
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private String message;
//    private Throwable throwable;
    @JsonProperty("http_status")
    private HttpStatus httpStatus;
    @JsonProperty("http_code")
    private int httpCode;
    private ZonedDateTime timestamp;
    private Map<String, Object> errors;

    public ErrorResponse(String message, HttpStatus httpStatus, int httpCode, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.httpCode = httpCode;
        this.timestamp = timestamp;
    }
}
