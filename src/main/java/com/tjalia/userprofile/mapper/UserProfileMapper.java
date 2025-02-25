package com.tjalia.userprofile.mapper;

import com.tjalia.userprofile.dto.request.UserProfileBody;
import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.entity.UserProfileEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfileResponse map(UserProfileEntity userProfileEntity);

    UserProfileEntity map(UserProfileResponse userProfileResponse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserProfileFromBody(UserProfileBody dto, @MappingTarget UserProfileEntity entity);

    List<UserProfileResponse> mapList(List<UserProfileEntity> entities);

    default Page<UserProfileResponse> mapPage(Page<UserProfileEntity> entityPage) {
        List<UserProfileResponse> responseList = mapList(entityPage.getContent());
        return new PageImpl<>(responseList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
