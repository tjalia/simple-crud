package com.tjalia.userprofile.service;

import com.tjalia.userprofile.dto.request.UserProfileBody;
import com.tjalia.userprofile.dto.response.UserProfileResponse;
import org.springframework.data.domain.Page;

public interface UserProfileService {

    UserProfileResponse createUserProfile(UserProfileBody userProfileBody);

    Page<UserProfileResponse> getAllUserProfile(int page, int size, String sortBy, String direction);
}
