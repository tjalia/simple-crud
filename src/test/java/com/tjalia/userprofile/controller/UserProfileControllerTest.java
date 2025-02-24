package com.tjalia.userprofile.controller;

import com.tjalia.userprofile.util.HeaderUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayName("User Profile web layer test")
class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Create User Profile should return 201 Created when request is successful")
    void createUserProfile_ShouldReturn201Created_WhenRequestIsSuccessful() throws Exception {

        String requestBody = """
        {
            "name":"tj",
            "emailAddress": "tj@emailAddress.com",
            "gender": "MALE",
            "birthDate":"1998-04-27",
            "role":"ADMIN"
        }
        """;

        mockMvc.perform(post("/api/v1/user-profile")
                        .headers(HeaderUtil.getClientCredentialHeaders())
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Get All User Profile should return 200 OK when request is successful")
    void getAllUserProfile_ShouldReturn200OK_WhenRequestIsSuccessful() throws Exception {

        mockMvc.perform(get("/api/v1/user-profile")
                        .headers(HeaderUtil.getClientCredentialHeaders()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}