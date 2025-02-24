package com.tjalia.userprofile.interactor.impl;

import com.tjalia.userprofile.constant.Gender;
import com.tjalia.userprofile.constant.Role;
import com.tjalia.userprofile.dto.request.UserProfileBody;
import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.entity.UserProfileEntity;
import com.tjalia.userprofile.exception.ApiException;
import com.tjalia.userprofile.interactor.CreateUserProfile;
import com.tjalia.userprofile.mapper.UserProfileMapper;
import com.tjalia.userprofile.repository.UserProfileRepository;
import com.tjalia.userprofile.validator.custom.UserProfileValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CreateUserProfileImpl implements CreateUserProfile {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final UserProfileValidator userProfileValidator;

    @Override
    @Transactional
    public UserProfileResponse execute(UserProfileBody userProfileBody) {

        userProfileValidator.validateEmail(userProfileBody.getEmailAddress());
        userProfileValidator.validateName(userProfileBody.getName());

        UserProfileEntity userProfileEntity = userProfileRepository.save(UserProfileEntity
                .builder()
                .name(userProfileBody.getName())
                .emailAddress(userProfileBody.getEmailAddress())
                .gender(Gender.valueOf(userProfileBody.getGender()))
                .birthDate(userProfileValidator.convertToLocalDate(userProfileBody.getBirthDate()))
                .role(Role.valueOf(userProfileBody.getRole()))
                .build());

        UserProfileResponse response = userProfileMapper.map(userProfileEntity);
        response.setAge(getAgeAsString(response.getBirthDate()));

        return response;
    }

    public static String getAgeAsString(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();

        return String.valueOf(age);
    }


}
