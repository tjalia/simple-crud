package com.tjalia.userprofile.interactor.impl;

import com.tjalia.userprofile.constant.Gender;
import com.tjalia.userprofile.constant.Role;
import com.tjalia.userprofile.dto.request.UserProfileBody;
import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.entity.UserProfileEntity;
import com.tjalia.userprofile.exception.ApiException;
import com.tjalia.userprofile.mapper.UserProfileMapper;
import com.tjalia.userprofile.repository.UserProfileRepository;
import com.tjalia.userprofile.validator.custom.UserProfileValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UpdateUserProfileImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserProfileMapper userProfileMapper;

    @Mock
    private UserProfileValidator userProfileValidator;

    @InjectMocks
    private UpdateUserProfileImpl updateUserProfile;

    private UserProfileEntity userProfileEntity;
    private UserProfileBody userProfileBody;
    private UserProfileResponse userProfileResponse;

    @BeforeEach
    void setUp() {
        userProfileEntity = UserProfileEntity.builder()
                .id(1L)
                .name("Old Name")
                .emailAddress("old@example.com")
                .birthDate(LocalDate.of(2000, 1, 1))
                .gender(Gender.MALE)
                .role(Role.USER)
                .build();

        userProfileBody = new UserProfileBody();
        userProfileBody.setName("Updated Name");
        userProfileBody.setEmailAddress("updated@example.com");
        userProfileBody.setBirthDate("2000-02-15");

        userProfileResponse = new UserProfileResponse();
        userProfileResponse.setId(1L);
        userProfileResponse.setName("Updated Name");
        userProfileResponse.setEmailAddress("updated@example.com");
    }

    @Test
    void execute_ShouldReturnUpdatedUserProfile_WhenValidRequest() {
        // Arrange
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(userProfileEntity));
        doNothing().when(userProfileValidator).validateEmail(userProfileBody.getEmailAddress());
        doNothing().when(userProfileValidator).validateName(userProfileBody.getName());
        when(userProfileValidator.convertToLocalDate(userProfileBody.getBirthDate()))
                .thenReturn(LocalDate.of(2000, 2, 15));

        doNothing().when(userProfileMapper).updateUserProfileFromBody(userProfileBody, userProfileEntity);
        when(userProfileRepository.save(userProfileEntity)).thenReturn(userProfileEntity);
        when(userProfileMapper.map(userProfileEntity)).thenReturn(userProfileResponse);

        // Act
        UserProfileResponse result = updateUserProfile.execute(1L, userProfileBody);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated Name", result.getName());
        assertEquals("updated@example.com", result.getEmailAddress());


        verify(userProfileRepository).findById(1L);
        verify(userProfileValidator).validateEmail(userProfileBody.getEmailAddress());
        verify(userProfileValidator).validateName(userProfileBody.getName());
        verify(userProfileValidator).convertToLocalDate(userProfileBody.getBirthDate());
        verify(userProfileMapper).updateUserProfileFromBody(userProfileBody, userProfileEntity);
        verify(userProfileRepository).save(userProfileEntity);
        verify(userProfileMapper).map(userProfileEntity);
    }

    @Test
    void execute_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(userProfileRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () ->
                updateUserProfile.execute(1L, userProfileBody)
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("User Profile not found.", exception.getMessage());

        // Verify interactions
        verify(userProfileRepository).findById(1L);
    }

}