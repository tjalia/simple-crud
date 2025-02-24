package com.tjalia.userprofile.interactor.impl;

import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.entity.UserProfileEntity;
import com.tjalia.userprofile.interactor.GetAllUserProfile;
import com.tjalia.userprofile.mapper.UserProfileMapper;
import com.tjalia.userprofile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllUserProfileImpl implements GetAllUserProfile {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    @Override
    public Page<UserProfileResponse> execute(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending(): Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<UserProfileEntity> entities = userProfileRepository.findAll(pageable);
        return userProfileMapper.mapPage(entities);
    }
}
