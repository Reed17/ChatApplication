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

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(final UserService userService, final AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public SignUpResponse signUp(final SignUpRequest signUpRequest, final HttpServletResponse response) {
        // todo exception handling

        userService.addNewUser(
                new UserDto(
                        signUpRequest.getUsername(),
                        signUpRequest.getEmail(),
                        signUpRequest.getPassword()));
        final User newUser = userService.findByEmail(signUpRequest.getEmail());
        final UserPrincipal userPrincipal = UserPrincipal.create(newUser);
        final UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        return new SignUpResponse(
                signUpRequest.getEmail(),
                signUpRequest.getUsername(),
                new Date()
        );
    }

    @Override
    public LoginResponse signIn(final LoginRequest loginRequest, final HttpServletResponse response) {
        // todo exception handling
        final UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        final Authentication authentication =
                authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new LoginResponse(loginRequest.getEmail(), new Date());
    }
}
