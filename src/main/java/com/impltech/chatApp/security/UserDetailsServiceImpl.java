package com.impltech.chatApp.security;

import com.impltech.chatApp.entity.User;
import com.impltech.chatApp.exceptions.UserNotFoundException;
import com.impltech.chatApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userWrapper = userRepository.findByUsername(username);
        if (!userWrapper.isPresent()) {
            throw new UserNotFoundException("User not found!");
        }
        return UserPrincipal.create(userWrapper.get());
    }
}
