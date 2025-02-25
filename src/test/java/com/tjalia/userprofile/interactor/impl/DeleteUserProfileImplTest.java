package com.tjalia.userprofile.interactor.impl;

import com.tjalia.userprofile.entity.UserProfileEntity;
import com.tjalia.userprofile.exception.ApiException;
import com.tjalia.userprofile.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DeleteUserProfileImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    DeleteUserProfileImpl deleteUserProfile;

    private UserProfileEntity userProfileEntity;

    @BeforeEach
    void setUp() {
        userProfileEntity = UserProfileEntity.builder()
                .id(1L)
                .name("Test User")
                .emailAddress("test@example.com")
                .build();
    }

    @Test
    void deleteUserProfile_ShouldDelete_WhenUserExists() {
        // Arrange
        Long userId = 1L;
        when(userProfileRepository.findById(userId)).thenReturn(Optional.of(userProfileEntity));
        doNothing().when(userProfileRepository).delete(userProfileEntity);

        // Act
        deleteUserProfile.execute(userId);

        // Assert
        verify(userProfileRepository, times(1)).delete(userProfileEntity);
    }

    @Test
    void deleteUserProfile_ShouldThrowException_WhenUserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        when(userProfileRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, () -> deleteUserProfile.execute(userId));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals("User Profile not found.", exception.getMessage());

        verify(userProfileRepository, never()).delete(any(UserProfileEntity.class));
    }

}