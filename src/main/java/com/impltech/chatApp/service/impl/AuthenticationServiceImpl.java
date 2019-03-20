package com.impltech.chatApp.service.impl;

import com.impltech.chatApp.dto.*;
import com.impltech.chatApp.entity.User;
import com.impltech.chatApp.security.UserPrincipal;
import com.impltech.chatApp.service.AuthenticationService;
import com.impltech.chatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
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
    public SignUpResponse signUp(SignUpRequest signUpRequest, HttpServletResponse response) {
        userService.addNewUser(
                new UserDto(
                        signUpRequest.getUsername(),
                        signUpRequest.getEmail(),
                        signUpRequest.getPassword()));
        User newUser = userService.findByEmail(signUpRequest.getEmail());
        final UserPrincipal userPrincipal = UserPrincipal.create(newUser);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        //response.setHeader("Authorization", "Basic " + );
        return new SignUpResponse(
                signUpRequest.getEmail(),
                signUpRequest.getUsername(),
                new Date()
        );
    }

    @Override
    public LoginResponse signIn(LoginRequest loginRequest, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication =
                authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        //response.setHeader("Authorization", authToken.toString());
        return new LoginResponse(loginRequest.getEmail(), new Date());
    }
}
