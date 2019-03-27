package com.impltech.chatApp.controller;

import com.impltech.chatApp.dto.LoginRequest;
import com.impltech.chatApp.dto.SignUpRequest;
import com.impltech.chatApp.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody final SignUpRequest signUpRequest,
                                    final HttpServletResponse response) throws Throwable {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authenticationService.signUp(signUpRequest, response));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody final LoginRequest loginRequest,
                                    final HttpServletResponse response) throws Throwable {
        return ResponseEntity
                .ok()
                .body(authenticationService.signIn(loginRequest, response));
    }
}
