package com.impltech.chatApp.service.impl;

import com.impltech.chatApp.dto.UserDto;
import com.impltech.chatApp.entity.User;
import com.impltech.chatApp.enums.Role;
import com.impltech.chatApp.mapper.UserMapper;
import com.impltech.chatApp.repository.UserRepository;
import com.impltech.chatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import static com.impltech.chatApp.utils.ValidationUtil.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final PasswordEncoder passwordEncoder, final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public User findByEmail(final String email) {
        checkEmailValidity(email);
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(final Long userId) {
        checkUserExistenceById(userRepository.existsById(userId));
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        checkUsernameValidity(username);
        isUserExistsByUsername(userRepository.existsByUsername(username));
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean existsByUsername(final String username) {
        checkUsernameValidity(username);
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(final String email) {
        checkEmailValidity(email);
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto addNewUser(final UserDto userDto) {
        isUserAlreadyExists(userRepository.existsByEmail(userDto.getEmail()));
        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        userDto.setRoles(Collections.singleton(Role.USER));
        final User user = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(user);
    }

    @Override
    public void delete(final Long userId) {
        checkUserExistenceById(userRepository.existsById(userId));
        userRepository.deleteById(userId);
    }


}
