package com.tjalia.userprofile.interactor;

import com.tjalia.userprofile.dto.response.base.MessageResponse;

public interface DeleteUserProfile {

    MessageResponse execute(Long id);
}
