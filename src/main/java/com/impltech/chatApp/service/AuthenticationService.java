package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.LoginRequest;
import com.impltech.chatApp.dto.LoginResponse;
import com.impltech.chatApp.dto.SignUpRequest;
import com.impltech.chatApp.dto.SignUpResponse;

public interface AuthenticationService {
    SignUpResponse signUp(SignUpRequest signUpRequest);
    LoginResponse signIn(LoginRequest loginRequest);
}
