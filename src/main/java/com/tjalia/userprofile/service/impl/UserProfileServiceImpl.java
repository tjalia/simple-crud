package com.tjalia.userprofile.service.impl;

import com.tjalia.userprofile.dto.request.UserProfileBody;
import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.interactor.CreateUserProfile;
import com.tjalia.userprofile.interactor.GetAllUserProfile;
import com.tjalia.userprofile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final CreateUserProfile createUserProfile;
    private final GetAllUserProfile getAllUserProfile;

    @Override
    public UserProfileResponse createUserProfile(UserProfileBody userProfileBody) {
        return createUserProfile.execute(userProfileBody);
    }

    @Override
    public Page<UserProfileResponse> getAllUserProfile(int page, int size, String sortBy, String direction) {
        return getAllUserProfile.execute(page, size, sortBy, direction);
    }
}
