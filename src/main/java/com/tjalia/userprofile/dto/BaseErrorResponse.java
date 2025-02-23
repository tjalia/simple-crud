package com.tjalia.userprofile.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class BaseErrorResponse {

    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime zonedDateTime;

    public BaseErrorResponse(String message,
                             HttpStatus httpStatus,
                             ZonedDateTime zonedDateTime) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
    }
}
