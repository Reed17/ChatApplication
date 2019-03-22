package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.UserDto;
import com.impltech.chatApp.entity.User;

import java.util.Optional;

public interface UserService {

    User findByEmail(final String email) throws Throwable;

    Optional<User> findById(final Long userId) throws Throwable;

    Optional<User> findByUsername(final String username) throws Throwable;

    Boolean existsByUsername(final String username) throws Throwable;

    Boolean existsByEmail(final String email) throws Throwable;

    UserDto addNewUser(final UserDto userDto);

    void delete(final Long userId) throws Throwable;

}
