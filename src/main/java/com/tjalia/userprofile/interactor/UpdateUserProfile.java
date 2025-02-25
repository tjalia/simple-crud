package com.tjalia.userprofile.interactor;

import com.tjalia.userprofile.dto.request.UserProfileBody;
import com.tjalia.userprofile.dto.response.UserProfileResponse;

public interface UpdateUserProfile {

    UserProfileResponse execute(Long id, UserProfileBody userProfileBody);
}
