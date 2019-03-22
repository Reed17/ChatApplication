package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.UserDto;
import com.impltech.chatApp.entity.User;

import java.util.Optional;

public interface UserService {

    User findByEmail(final String email);

    Optional<User> findById(final Long userId);

    Optional<User> findByUsername(final String username);

    Boolean existsByUsername(final String username);

    Boolean existsByEmail(final String email);

    UserDto addNewUser(final UserDto userDto);

    void delete(final Long userId);

}
