package com.tjalia.userprofile.interactor.impl;

import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.entity.UserProfileEntity;
import com.tjalia.userprofile.exception.ApiException;
import com.tjalia.userprofile.interactor.GetUserProfile;
import com.tjalia.userprofile.mapper.UserProfileMapper;
import com.tjalia.userprofile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserProfileImpl implements GetUserProfile {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    @Override
    public UserProfileResponse execute(Long id) {
        UserProfileEntity entity = userProfileRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST,"User Profile not found."));
        return userProfileMapper.map(entity);
    }
}
