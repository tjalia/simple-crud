package com.tjalia.userprofile.interactor.impl;

import com.tjalia.userprofile.constant.Gender;
import com.tjalia.userprofile.constant.Role;
import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.entity.UserProfileEntity;
import com.tjalia.userprofile.mapper.UserProfileMapper;
import com.tjalia.userprofile.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class GetAllUserProfileImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserProfileMapper userProfileMapper;

    @InjectMocks
    private GetAllUserProfileImpl getAllUserProfile;

    @Test
    void execute_ShouldReturnPageOfUserProfileResponse_WhenValidRequest() {
        // Arrange
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String direction = "asc";

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        List<UserProfileEntity> userList = List.of(
                new UserProfileEntity(1L, "Ted", "ted@email.com", Gender.MALE, LocalDate.of(1995, 5, 20), Role.USER),
                new UserProfileEntity(2L, "Rose", "rose@email.com", Gender.FEMALE, LocalDate.of(1992, 8, 15), Role.ADMIN)
        );
        Page<UserProfileEntity> userPage = new PageImpl<>(userList, pageable, userList.size());

        List<UserProfileResponse> responseList = List.of(
                UserProfileResponse.builder()
                        .id(1L)
                        .name("Alice")
                        .emailAddress("alice@email.com")
                        .gender(Gender.FEMALE)
                        .birthDate(LocalDate.of(1995, 5, 20))
                        .age("28")
                        .role(Role.USER)
                        .build(),

                UserProfileResponse.builder()
                        .id(2L)
                        .name("Bob")
                        .emailAddress("bob@email.com")
                        .gender(Gender.MALE)
                        .birthDate(LocalDate.of(1992, 8, 15))
                        .age("31")
                        .role(Role.ADMIN)
                        .build()
        );
        Page<UserProfileResponse> responsePage = new PageImpl<>(responseList, pageable, responseList.size());

        when(userProfileRepository.findAll(pageable)).thenReturn(userPage);
        when(userProfileMapper.mapPage(userPage)).thenReturn(responsePage);

        // Act
        Page<UserProfileResponse> result = getAllUserProfile.execute(page, size, sortBy, direction);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("Alice", result.getContent().get(0).getName());
        assertEquals("Bob", result.getContent().get(1).getName());

        verify(userProfileRepository).findAll(pageable);
        verify(userProfileMapper).mapPage(userPage);
    }
}