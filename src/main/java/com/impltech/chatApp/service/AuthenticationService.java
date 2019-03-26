package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.*;

import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(final SignUpRequest signUpRequest, final HttpServletResponse response) throws Throwable;
    JwtAuthenticationResponse signIn(final LoginRequest loginRequest, final HttpServletResponse response) throws Throwable;
}
