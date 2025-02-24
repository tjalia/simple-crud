package com.tjalia.userprofile.interactor;

import com.tjalia.userprofile.dto.request.UserProfileBody;
import com.tjalia.userprofile.dto.response.UserProfileResponse;

public interface CreateUserProfile {

    UserProfileResponse execute(UserProfileBody userProfileBody);
}
