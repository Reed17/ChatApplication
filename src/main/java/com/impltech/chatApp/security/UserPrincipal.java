package com.impltech.chatApp.security;

import com.impltech.chatApp.entity.User;
import com.impltech.chatApp.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private Long userId;
    private String username;
    private transient String email;
    private transient String password;
    private Set<? extends GrantedAuthority> authorities = new HashSet<>();

    public UserPrincipal(Long userId, String username, String email, String password, Set<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        Set<GrantedAuthority> userAuthorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());
        return new UserPrincipal(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                userAuthorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
