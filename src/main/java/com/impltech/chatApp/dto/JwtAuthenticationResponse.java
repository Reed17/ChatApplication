package com.impltech.chatApp.dto;

public class JwtAuthenticationResponse {
    private String accessToken;
    private SignUpResponse signUpResponse;
    private LoginResponse loginResponse;

    public JwtAuthenticationResponse(String accessToken, SignUpResponse signUpResponse) {
        this.accessToken = accessToken;
        this.signUpResponse = signUpResponse;
    }

    public JwtAuthenticationResponse(String accessToken, LoginResponse loginResponse) {
        this.accessToken = accessToken;
        this.loginResponse = loginResponse;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public SignUpResponse getSignUpResponse() {
        return signUpResponse;
    }

    public void setSignUpResponse(SignUpResponse signUpResponse) {
        this.signUpResponse = signUpResponse;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }
}
