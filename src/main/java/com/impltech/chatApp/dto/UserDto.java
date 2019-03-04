package com.impltech.chatApp.dto;

import com.impltech.chatApp.enums.Role;

import java.util.HashSet;
import java.util.Set;

public class UserDto {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();

    public UserDto() {
    }

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserDto(String username, String email, String password, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UserDto(Long userId, String username, String email, String password, Set<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
