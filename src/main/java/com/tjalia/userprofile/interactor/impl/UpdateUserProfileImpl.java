package com.tjalia.userprofile.interactor.impl;

import com.tjalia.userprofile.dto.request.UserProfileBody;
import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.entity.UserProfileEntity;
import com.tjalia.userprofile.exception.ApiException;
import com.tjalia.userprofile.interactor.UpdateUserProfile;
import com.tjalia.userprofile.mapper.UserProfileMapper;
import com.tjalia.userprofile.repository.UserProfileRepository;
import com.tjalia.userprofile.validator.custom.UserProfileValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserProfileImpl implements UpdateUserProfile {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final UserProfileValidator userProfileValidator;

    @Override
    public UserProfileResponse execute(Long id, UserProfileBody userProfileBody) {
        UserProfileEntity entity = userProfileRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST,"User Profile not found."));

        if (userProfileBody.getEmailAddress() != null) {
            userProfileValidator.validateEmail(userProfileBody.getEmailAddress());
            entity.setEmailAddress(userProfileBody.getEmailAddress());
        }

        if (userProfileBody.getName() != null) {
            userProfileValidator.validateName(userProfileBody.getName());
            entity.setName(userProfileBody.getName());
        }

        if (userProfileBody.getBirthDate() != null) {
            entity.setBirthDate(userProfileValidator.convertToLocalDate(userProfileBody.getBirthDate()));
        }

        userProfileMapper.updateUserProfileFromBody(userProfileBody, entity);

        UserProfileEntity updatedEntity = userProfileRepository.save(entity);

        return userProfileMapper.map(updatedEntity);
    }
}
