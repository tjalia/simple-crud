package com.tjalia.userprofile.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
public class ApiException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;
    private String description;
    private String code;
    private String heading;
    private String reference;
    private Map<String, String> details;

    public ApiException(Throwable cause) { super(cause); }

    public ApiException(String message) {
        this(message, "");
    }

    public ApiException(String message, String description) {
        this(HttpStatus.UNPROCESSABLE_ENTITY, message, description);
    }

    public ApiException(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null);
    }

    public ApiException(String message, String description, String code, String heading) {
        super(message);
        this.httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        this.message = message;
        this.description = description;
        this.code = code;
        this.heading = heading;
    }

    public ApiException(HttpStatus httpStatus, String message, String description) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
        this.description = description;
    }

    public ApiException(HttpStatus httpStatus, String message, String description, String code, String heading) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
        this.description = description;
        this.code = code;
        this.heading = heading;
    }

    public ApiException(HttpStatus httpStatus, String message, String description, String code, String heading, Map<String, String> details) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
        this.description = description;
        this.code = code;
        this.heading = heading;
        this.details = details;
    }
}
