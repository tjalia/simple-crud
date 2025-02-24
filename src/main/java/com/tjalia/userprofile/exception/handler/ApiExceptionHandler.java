package com.tjalia.userprofile.exception.handler;

import com.tjalia.userprofile.dto.BaseErrorResponse;
import com.tjalia.userprofile.exception.ApiException;
import com.tjalia.userprofile.exception.ClientCredentialiException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ClientCredentialiException.class)
    public ResponseEntity<Object> handleClientCredentialException(ClientCredentialiException ex){
        log.error("Client Credential Exception: {}", ex.getMessage(), ex);

        BaseErrorResponse baseErrorResponse = new BaseErrorResponse(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(baseErrorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Map<String, Object>> handleApiException(ApiException ex) {

        log.error("API Exception: {} - {}", ex.getMessage(), ex.getDescription(), ex);

        Map<String, Object> errorResponse = Stream.of(
                        new AbstractMap.SimpleEntry<>("message", ex.getMessage()),
                        new AbstractMap.SimpleEntry<>("description", ex.getDescription()),
                        new AbstractMap.SimpleEntry<>("code", ex.getCode()),
                        new AbstractMap.SimpleEntry<>("heading", ex.getHeading()),
                        new AbstractMap.SimpleEntry<>("reference", ex.getReference()),
                        new AbstractMap.SimpleEntry<>("details", ex.getDetails())
                ).filter(entry -> entry.getValue() != null)  // Filter out null values
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {

        log.error("Unhandled Exception: ", ex);

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "An unexpected error occurred");
        errorResponse.put("details", ex.getMessage());

        return ResponseEntity.status(500).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put("message", error.getDefaultMessage()) // ⬅️ Set "message" field
        );

        log.error("Validation Exception: {} ", errors, ex);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        log.error("Constraint Exception: {} ", errors, ex);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid value: '" + ex.getValue() + "' for parameter: " + ex.getName();
        return ResponseEntity.badRequest().body(Map.of("message", message));
    }

}
