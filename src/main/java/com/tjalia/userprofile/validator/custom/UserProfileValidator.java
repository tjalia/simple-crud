package com.tjalia.userprofile.validator.custom;

import com.tjalia.userprofile.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

@Component
public class UserProfileValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Email address cannot be empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid email format");
        }
    }

    public void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Name cannot be empty");
        }
        if (name.length() > 100) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Name must be at most 100 characters");
        }
    }

    public LocalDate convertToLocalDate(String birthDate) {
        try {
            return LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid date format: " + birthDate);
        }
    }
}
