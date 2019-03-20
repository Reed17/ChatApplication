package com.impltech.chatApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.impltech.chatApp.dto.LoginRequest;
import com.impltech.chatApp.dto.SignUpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = {"classpath:application-test.yml"})
public class AuthenticationControllerIntegrationTest {

    private static final String API_AUTH_SIGNUP = "/api/auth/signup";
    private static final String API_AUTH_SIGNIN = "/api/auth/signin";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    void whenUserSignUpWithValidCredentialsThenReturnStatusIsCreated() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("test@gmail.com", "test", "1qazxsw2");

        this.mockMvc.perform(post(API_AUTH_SIGNUP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("test@gmail.com")))
                .andExpect(jsonPath("$.username", is("test")));
    }

    @Test
    void whenRegisteredUserTryToSignInThenReturnStatusIsOk() throws Exception {
        registerNewUser();

        LoginRequest loginRequest = new LoginRequest("John@gmail.com", "1qazxsw2");

        this.mockMvc.perform(post(API_AUTH_SIGNIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void whenUnregisteredUserTriesToSignInThenReturnStatusIsUnauthorized() throws Exception {
        LoginRequest loginRequest = new LoginRequest("unexisted@gmail.com", "password");

        this.mockMvc.perform(post(API_AUTH_SIGNIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    private void registerNewUser() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("John@gmail.com", "John", "1qazxsw2");

        this.mockMvc.perform(post(API_AUTH_SIGNUP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated());
    }
}
