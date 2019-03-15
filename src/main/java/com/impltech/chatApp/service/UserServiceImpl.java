package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.UserDto;
import com.impltech.chatApp.entity.User;
import com.impltech.chatApp.enums.Role;
import com.impltech.chatApp.mapper.UserMapper;
import com.impltech.chatApp.repository.UserRepository;
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
    public UserDto findByEmail(String email) {
        return null;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Boolean existsByUsername(String username) {
        return null;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return null;
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
        userRepository.deleteById(userId);
    }
}
