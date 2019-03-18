package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.LoginRequest;
import com.impltech.chatApp.dto.LoginResponse;
import com.impltech.chatApp.dto.SignUpRequest;
import com.impltech.chatApp.dto.SignUpResponse;

import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    SignUpResponse signUp(SignUpRequest signUpRequest, HttpServletResponse response);
    LoginResponse signIn(LoginRequest loginRequest, HttpServletResponse response);
}
