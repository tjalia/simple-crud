package com.tjalia.userprofile.service.impl;

import com.tjalia.userprofile.dto.request.UserProfileBody;
import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.interactor.CreateUserProfile;
import com.tjalia.userprofile.interactor.GetAllUserProfile;
import com.tjalia.userprofile.interactor.GetUserProfile;
import com.tjalia.userprofile.interactor.UpdateUserProfile;
import com.tjalia.userprofile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final CreateUserProfile createUserProfile;
    private final GetAllUserProfile getAllUserProfile;
    private final GetUserProfile getUserProfile;
    private final UpdateUserProfile updateUserProfile;

    @Override
    public UserProfileResponse createUserProfile(UserProfileBody userProfileBody) {
        return createUserProfile.execute(userProfileBody);
    }

    @Override
    public Page<UserProfileResponse> getAllUserProfile(int page, int size, String sortBy, String direction) {
        return getAllUserProfile.execute(page, size, sortBy, direction);
    }

    @Override
    public UserProfileResponse getUserProfile(Long id) {
        return getUserProfile.execute(id);
    }

    @Override
    public UserProfileResponse updateUserProfile(Long id, UserProfileBody userProfileBody) {
        return updateUserProfile.execute(id, userProfileBody);
    }
}
