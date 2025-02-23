package com.tjalia.userprofile.exception.handler;

import com.tjalia.userprofile.dto.BaseErrorResponse;
import com.tjalia.userprofile.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException ex){
        BaseErrorResponse baseErrorResponse = new BaseErrorResponse(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(baseErrorResponse, HttpStatus.UNAUTHORIZED);
    }
}
