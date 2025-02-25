package com.tjalia.userprofile.interactor.impl;

import com.tjalia.userprofile.constant.Gender;
import com.tjalia.userprofile.constant.Role;
import com.tjalia.userprofile.dto.request.UserProfileBody;
import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.entity.UserProfileEntity;
import com.tjalia.userprofile.mapper.UserProfileMapper;
import com.tjalia.userprofile.repository.UserProfileRepository;
import com.tjalia.userprofile.validator.custom.UserProfileValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CreateUserProfileImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserProfileValidator userProfileValidator;

    @Mock
    private UserProfileMapper userProfileMapper;

    @InjectMocks
    private CreateUserProfileImpl createUserProfile;

    private UserProfileBody userProfileBody;
    private UserProfileEntity userProfileEntity;
    private UserProfileResponse userProfileResponse;

    @BeforeEach
    void setUp() {

        userProfileBody = UserProfileBody.builder()
                .name("Test User")
                .emailAddress("test@example.com")
                .gender("MALE")
                .birthDate("1998-04-27")
                .role("ADMIN")
                .build();


        LocalDate birthDate = LocalDate.of(1998, 4, 27);
        when(userProfileValidator.convertToLocalDate(userProfileBody.getBirthDate()))
                .thenReturn(birthDate);

        userProfileEntity = UserProfileEntity.builder()
                .name(userProfileBody.getName())
                .emailAddress(userProfileBody.getEmailAddress())
                .gender(Gender.valueOf(userProfileBody.getGender()))
                .birthDate(birthDate)
                .role(Role.valueOf(userProfileBody.getRole()))
                .build();

        userProfileResponse = UserProfileResponse.builder()
                .id(userProfileEntity.getId())
                .name(userProfileEntity.getName())
                .emailAddress(userProfileEntity.getEmailAddress())
                .gender(userProfileEntity.getGender())
                .birthDate(userProfileEntity.getBirthDate())
                .role(userProfileEntity.getRole())
                .build();
    }

    @Test
    void execute_ShouldReturnUserProfileResponse_WhenValidRequest() {
        // Arrange
        doNothing().when(userProfileValidator).validateEmail(userProfileBody.getEmailAddress());
        doNothing().when(userProfileValidator).validateName(userProfileBody.getName());
        when(userProfileValidator.convertToLocalDate(userProfileBody.getBirthDate()))
                .thenReturn(userProfileEntity.getBirthDate());
        when(userProfileRepository.save(any(UserProfileEntity.class))).thenReturn(userProfileEntity);
        when(userProfileMapper.map(any(UserProfileEntity.class))).thenReturn(userProfileResponse);

        // ACT
        UserProfileResponse result = createUserProfile.execute(userProfileBody);

        // Assert
        assertNotNull(result);
        assertEquals(userProfileBody.getName(), result.getName());
        assertEquals(userProfileBody.getEmailAddress(), result.getEmailAddress());
        assertEquals(Gender.valueOf(userProfileBody.getGender()), result.getGender());
        assertEquals(Role.valueOf(userProfileBody.getRole()), result.getRole());


        verify(userProfileValidator).validateEmail(userProfileBody.getEmailAddress());
        verify(userProfileValidator).validateName(userProfileBody.getName());
        verify(userProfileRepository).save(any(UserProfileEntity.class));
        verify(userProfileMapper).map(any(UserProfileEntity.class));
    }

}