package com.impltech.chatApp.controller;

import com.impltech.chatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasAnyAuthority('USER')")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/mail/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") final String email) throws Throwable {
        return ResponseEntity.ok().body(userService.findByEmail(email));
    }

    @GetMapping("/name/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") final String username) throws Throwable {
        return ResponseEntity.ok().body(userService.findByUsername(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") final Long userId) throws Throwable {
        return ResponseEntity.ok().body(userService.findById(userId));
    }
}
