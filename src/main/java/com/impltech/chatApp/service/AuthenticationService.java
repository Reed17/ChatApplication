package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.LoginRequest;
import com.impltech.chatApp.dto.LoginResponse;
import com.impltech.chatApp.dto.SignUpRequest;
import com.impltech.chatApp.dto.SignUpResponse;

import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    SignUpResponse signUp(final SignUpRequest signUpRequest, final HttpServletResponse response);
    LoginResponse signIn(final LoginRequest loginRequest, final HttpServletResponse response);
}
