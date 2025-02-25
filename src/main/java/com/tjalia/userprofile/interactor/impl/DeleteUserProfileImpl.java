package com.tjalia.userprofile.interactor.impl;

import com.tjalia.userprofile.dto.response.base.MessageResponse;
import com.tjalia.userprofile.entity.UserProfileEntity;
import com.tjalia.userprofile.exception.ApiException;
import com.tjalia.userprofile.interactor.DeleteUserProfile;
import com.tjalia.userprofile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserProfileImpl implements DeleteUserProfile {

    private final UserProfileRepository userProfileRepository;

    @Override
    public MessageResponse execute(Long id) {

        UserProfileEntity userProfileEntity = userProfileRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "User Profile not found."));
        userProfileRepository.delete(userProfileEntity);

        return MessageResponse.builder()
                .message("User Profile has been deleted").build();
    }
}
