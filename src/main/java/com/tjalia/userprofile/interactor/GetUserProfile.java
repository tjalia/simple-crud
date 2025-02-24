package com.tjalia.userprofile.interactor;

import com.tjalia.userprofile.dto.response.UserProfileResponse;

public interface GetUserProfile {
    UserProfileResponse execute(Long id);
}
