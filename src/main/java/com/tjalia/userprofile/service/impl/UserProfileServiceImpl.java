package com.tjalia.userprofile.service.impl;

import com.tjalia.userprofile.dto.request.UserProfileBody;
import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.interactor.CreateUserProfile;
import com.tjalia.userprofile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final CreateUserProfile createUserProfile;

    @Override
    public UserProfileResponse createUserProfile(UserProfileBody userProfileBody) {
        return createUserProfile.execute(userProfileBody);
    }
}
