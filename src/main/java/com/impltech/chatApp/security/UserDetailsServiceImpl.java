package com.impltech.chatApp.security;

import com.impltech.chatApp.entity.User;
import com.impltech.chatApp.enums.Message;
import com.impltech.chatApp.exceptions.UserNotFoundException;
import com.impltech.chatApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(username);
        if (!userRepository.existsByEmail(username)) {
            final String errMsg = Message.USER_NOT_FOUND.getMessage();
            LOG.error(errMsg);
            throw new UserNotFoundException(errMsg);
        }
        return UserPrincipal.create(user);
    }
}
