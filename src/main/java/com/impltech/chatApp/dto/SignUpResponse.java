package com.impltech.chatApp.dto;

import java.util.Date;

public class SignUpResponse {
    private String email;
    private String username;
    private Date registrationDate;

    public SignUpResponse() {
    }

    public SignUpResponse(String email, String username, Date registrationDate) {
        this.email = email;
        this.username = username;
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
