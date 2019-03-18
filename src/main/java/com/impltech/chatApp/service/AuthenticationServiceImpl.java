package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.*;
import com.impltech.chatApp.entity.User;
import com.impltech.chatApp.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        userService.addNewUser(
                new UserDto(
                        signUpRequest.getUsername(),
                        signUpRequest.getEmail(),
                        signUpRequest.getPassword()));
        User newUser = userService.findByEmail(signUpRequest.getEmail());
        UserPrincipal.create(newUser);
        return new SignUpResponse(
                signUpRequest.getEmail(),
                signUpRequest.getUsername(),
                new Date()
        );
    }

    @Override
    public LoginResponse signIn(LoginRequest loginRequest) {

        return null;
    }
}
