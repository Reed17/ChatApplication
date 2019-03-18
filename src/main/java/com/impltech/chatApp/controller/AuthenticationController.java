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

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(authenticationService.signIn(loginRequest));

    }
}
