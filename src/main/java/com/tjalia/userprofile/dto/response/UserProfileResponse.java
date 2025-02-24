package com.tjalia.userprofile.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tjalia.userprofile.constant.Gender;
import com.tjalia.userprofile.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileResponse {

    private Long id;
    private String name;
    private String emailAddress;
    private Gender gender;
    private LocalDate birthDate;
    private String age = getAge();
    private Role role;

    public String getAge() {
        if (birthDate == null) return null;
        return String.valueOf(Period.between(birthDate, LocalDate.now()).getYears());
    }
}
