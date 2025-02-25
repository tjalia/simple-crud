package com.tjalia.userprofile.service;

import com.tjalia.userprofile.dto.request.UserProfileBody;
import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.dto.response.base.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserProfileService {

    UserProfileResponse createUserProfile(UserProfileBody userProfileBody);

    Page<UserProfileResponse> getAllUserProfile(int page, int size, String sortBy, String direction);

    UserProfileResponse getUserProfile(Long id);

    UserProfileResponse updateUserProfile(Long id, UserProfileBody userProfileBody);

    MessageResponse deleteUserProfile(Long id);
}
