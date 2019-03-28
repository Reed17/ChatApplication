package com.impltech.chatApp.service.impl;

import com.impltech.chatApp.config.HeaderProperties;
import com.impltech.chatApp.config.JwtProperties;
import com.impltech.chatApp.dto.*;
import com.impltech.chatApp.entity.User;
import com.impltech.chatApp.enums.Message;
import com.impltech.chatApp.exceptions.UserAlreadyExistsException;
import com.impltech.chatApp.exceptions.UserNotExistsException;
import com.impltech.chatApp.security.JwtProvider;
import com.impltech.chatApp.security.UserPrincipal;
import com.impltech.chatApp.service.AuthenticationService;
import com.impltech.chatApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;
    private final HeaderProperties headerProperties;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(JwtProvider jwtProvider, JwtProperties jwtProperties, HeaderProperties headerProperties, UserService userService, AuthenticationManager authenticationManager) {
        this.jwtProvider = jwtProvider;
        this.jwtProperties = jwtProperties;
        this.headerProperties = headerProperties;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    @Override
    public JwtAuthenticationResponse signUp(final SignUpRequest signUpRequest, final HttpServletResponse response) throws Throwable {
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            final String errMsg = Message.USER_ALREADY_EXISTS.getMessage();
            LOG.error(errMsg);
            throw new UserAlreadyExistsException(errMsg);
        }

        userService.addNewUser(
                new UserDto(
                        signUpRequest.getUsername(),
                        signUpRequest.getEmail(),
                        signUpRequest.getPassword()));

        final User newUser = userService.findByEmail(signUpRequest.getEmail());

        final UserPrincipal userPrincipal = UserPrincipal.create(newUser);

        final String accessToken = jwtProvider.generateAccessToken(userPrincipal);
        response.setHeader(headerProperties.getName(), headerProperties.getType() + accessToken);
        return new JwtAuthenticationResponse(accessToken,
                new SignUpResponse(
                        signUpRequest.getEmail(),
                        signUpRequest.getUsername(),
                        new Date()
                ));
    }

    @Transactional
    @Override
    public JwtAuthenticationResponse signIn(final LoginRequest loginRequest, final HttpServletResponse response) throws Throwable {
        if (!userService.existsByEmail(loginRequest.getEmail())) {
            final String errMsg = Message.USER_WITH_SUCH_EMAIL_NOT_EXISTS.getMessage();
            LOG.error(errMsg);
            throw new UserNotExistsException(errMsg);
        }

        final UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        final Authentication authentication =
                authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        final String accessToken = jwtProvider.generateAccessToken(userPrincipal);
        response.setHeader(headerProperties.getName(), headerProperties.getType() + accessToken);
        return new JwtAuthenticationResponse(accessToken,
                new LoginResponse(
                        loginRequest.getEmail(),
                        new Date()));
    }
}
