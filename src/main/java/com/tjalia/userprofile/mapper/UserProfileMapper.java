package com.tjalia.userprofile.mapper;

import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.entity.UserProfileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfileResponse map(UserProfileEntity userProfileEntity);

    UserProfileEntity map(UserProfileResponse userProfileResponse);
}
