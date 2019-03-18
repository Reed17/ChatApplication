package com.impltech.chatApp.dto;

import java.util.Date;

public class LoginResponse {
    private String email;
    private Date loginDate;

    public LoginResponse() {
    }

    public LoginResponse(String email, Date loginDate) {
        this.email = email;
        this.loginDate = loginDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
}
