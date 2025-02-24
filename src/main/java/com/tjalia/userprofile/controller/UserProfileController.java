package com.tjalia.userprofile.controller;

import com.tjalia.userprofile.dto.request.UserProfileBody;
import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("/v1/user-profile")
    @ResponseStatus(HttpStatus.CREATED)
    public UserProfileResponse createUserProfile(@Valid @RequestBody UserProfileBody userProfileBody){
        return userProfileService.createUserProfile(userProfileBody);
    }

    @GetMapping("/v1/user-profile")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserProfileResponse> getAllUserProfile(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction){
        return userProfileService.getAllUserProfile(page, size, sortBy, direction);
    }

    @GetMapping("/v1/user-profile/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserProfileResponse getUserProfile(
            @PathVariable Long id){
        return userProfileService.getUserProfile(id);
    }

}
