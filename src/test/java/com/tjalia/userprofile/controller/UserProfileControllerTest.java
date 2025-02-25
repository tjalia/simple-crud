package com.tjalia.userprofile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tjalia.userprofile.constant.Gender;
import com.tjalia.userprofile.dto.request.UserProfileBody;
import com.tjalia.userprofile.dto.response.UserProfileResponse;
import com.tjalia.userprofile.service.UserProfileService;
import com.tjalia.userprofile.util.HeaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayName("User Profile web layer test")
class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserProfileService userProfileService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // For LocalDate support
    }

    @Test
    @DisplayName("Create User Profile should return 201 Created when request is successful")
    void createUserProfile_ShouldReturn201Created_WhenRequestIsSuccessful() throws Exception {
        // Given
        String requestBody = """
        {
            "name":"tj",
            "emailAddress": "tj@emailAddress.com",
            "gender": "MALE",
            "birthDate":"1998-04-27",
            "role":"ADMIN"
        }
        """;

        // When & Then
        mockMvc.perform(post("/api/v1/user-profile")
                        .headers(HeaderUtil.getClientCredentialHeaders())
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Get All User Profile should return 200 OK when request is successful")
    void getAllUserProfile_ShouldReturn200OK_WhenRequestIsSuccessful() throws Exception {

        // When & Then
        mockMvc.perform(get("/api/v1/user-profile")
                        .headers(HeaderUtil.getClientCredentialHeaders()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get User Profile should return 200 OK when request is successful")
    void getUserProfile_ShouldReturn200OK_WhenRequestIsSuccessful() throws Exception {
        // Given
        Long userId = 1L;
        UserProfileResponse mockResponse = new UserProfileResponse();
        mockResponse.setId(userId);
        mockResponse.setName("TJ");

        when(userProfileService.getUserProfile(userId)).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/v1/user-profile/{id}", userId)
                        .headers(HeaderUtil.getClientCredentialHeaders()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("TJ"));
    }

    @Test
    @DisplayName("Update User Profile should return 200 OK when request is successful")
    void updateUserProfile_ShouldReturn200OK_WhenRequestIsSuccessful() throws Exception {
        // Given
        Long userId = 1L;
        UserProfileBody requestBody = new UserProfileBody();
        requestBody.setName("Updated Name");
        requestBody.setGender("FEMALE");

        UserProfileResponse response = new UserProfileResponse();
        response.setId(userId);
        response.setName("Updated Name");
        response.setGender(Gender.FEMALE);

        when(userProfileService.updateUserProfile(eq(userId), any(UserProfileBody.class)))
                .thenReturn(response);

        // When & Then
        mockMvc.perform(put("/api/v1/user-profile/{id}", userId)
                        .headers(HeaderUtil.getClientCredentialHeaders())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.gender").value("FEMALE"))
                .andDo(print());

        verify(userProfileService).updateUserProfile(eq(userId), any(UserProfileBody.class));
    }

}