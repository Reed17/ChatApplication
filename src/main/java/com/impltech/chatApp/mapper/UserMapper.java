package com.impltech.chatApp.mapper;

import com.impltech.chatApp.dto.UserDto;
import com.impltech.chatApp.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserMapper implements BaseMapper<User, UserDto> {

    @Override
    public UserDto toDto(final User entity) {
        final UserDto userDto = new UserDto();
        userDto.setUserId(entity.getUserId());
        userDto.setUsername(entity.getUsername());
        userDto.setEmail(entity.getEmail());
        userDto.setPassword(entity.getPassword());
        return userDto;
    }

    @Override
    public User toEntity(final UserDto dto) {
        final User user = new User();
        user.setUserId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

}
