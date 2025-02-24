package com.tjalia.userprofile.interactor.impl;

import com.tjalia.userprofile.constant.Gender;
import com.tjalia.userprofile.constant.Role;
import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.entity.UserProfileEntity;
import com.tjalia.userprofile.exception.ApiException;
import com.tjalia.userprofile.mapper.UserProfileMapper;
import com.tjalia.userprofile.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class GetUserProfileImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserProfileMapper userProfileMapper;

    @InjectMocks
    private GetUserProfileImpl getUserProfile;

    private UserProfileEntity userProfileEntity;
    private UserProfileResponse userProfileResponse;

    @BeforeEach
    void setUp() {
        LocalDate birthDate = LocalDate.of(1998, 4, 27);

        userProfileEntity = UserProfileEntity.builder()
                .id(1L)
                .name("Test User")
                .emailAddress("test@example.com")
                .gender(Gender.MALE)
                .birthDate(birthDate)
                .role(Role.ADMIN)
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
    void execute_ShouldReturnUserProfileResponse_WhenUserExists() {
        // Arrange
        Long userId = 1L;

        when(userProfileRepository.findById(userId)).thenReturn(Optional.of(userProfileEntity));
        when(userProfileMapper.map(userProfileEntity)).thenReturn(userProfileResponse);

        // Act
        UserProfileResponse result = getUserProfile.execute(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userProfileResponse.getId(), result.getId());
        assertEquals(userProfileResponse.getName(), result.getName());
        assertEquals(userProfileResponse.getEmailAddress(), result.getEmailAddress());
        assertEquals(userProfileResponse.getGender(), result.getGender());
        assertEquals(userProfileResponse.getBirthDate(), result.getBirthDate());
        assertEquals(userProfileResponse.getRole(), result.getRole());

        int expectedAge = Period.between(userProfileEntity.getBirthDate(), LocalDate.now()).getYears();
        assertEquals(String.valueOf(expectedAge), result.getAge());

        verify(userProfileRepository).findById(userId);
        verify(userProfileMapper).map(userProfileEntity);
    }

    @Test
    void execute_ShouldThrowApiException_WhenUserNotFound() {
        // Arrange
        Long invalidUserId = 99L;
        when(userProfileRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> {
            getUserProfile.execute(invalidUserId);
        });

        assertEquals("User Profile not found.", exception.getMessage());

        verify(userProfileRepository).findById(invalidUserId);
    }

}