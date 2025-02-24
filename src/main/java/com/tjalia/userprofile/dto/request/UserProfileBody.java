package com.tjalia.userprofile.dto.request;

import com.tjalia.userprofile.constant.Gender;
import com.tjalia.userprofile.constant.Role;
import com.tjalia.userprofile.validator.DateValidator;
import com.tjalia.userprofile.validator.EnumValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileBody {

    private String name;

    private String emailAddress;

    @EnumValidator(enumClass = Gender.class, message = "Gender must be MALE or FEMALE")
    private String gender;

    private String birthDate;

    @EnumValidator(enumClass = Role.class, message = "Role must be ADMIN or USER")
    private String role;
}
