package com.impltech.chatApp.service.impl;

import com.impltech.chatApp.dto.UserDto;
import com.impltech.chatApp.entity.User;
import com.impltech.chatApp.enums.Role;
import com.impltech.chatApp.exceptions.InvalidEmailException;
import com.impltech.chatApp.exceptions.InvalidUsernameException;
import com.impltech.chatApp.exceptions.UserNotFoundException;
import com.impltech.chatApp.mapper.UserMapper;
import com.impltech.chatApp.repository.UserRepository;
import com.impltech.chatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public User findByEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new UserNotFoundException("User doesn't exists!");
        }
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found!");
        }
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if (!userRepository.existsByUsername(username)) {
            throw new UserNotFoundException("User not found!");
        }
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean existsByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new InvalidUsernameException("Invalid or empty username!");
        }
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new InvalidEmailException("Invalid or empty email!");
        }
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto addNewUser(UserDto userDto) {
        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        userDto.setRoles(Collections.singleton(Role.USER));
        User user = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(user);
    }

    @Override
    public void delete(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found!");
        }
        userRepository.deleteById(userId);
    }
}
