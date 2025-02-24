package com.tjalia.userprofile.interactor;

import com.tjalia.userprofile.dto.response.UserProfileResponse;
import org.springframework.data.domain.Page;

public interface GetAllUserProfile {

    Page<UserProfileResponse> execute(int page, int size, String sortBy, String direction);
}
