package com.impltech.chatApp.utils;

import com.impltech.chatApp.exceptions.InvalidEmailException;
import com.impltech.chatApp.exceptions.InvalidUsernameException;
import com.impltech.chatApp.exceptions.UserAlreadyExistsException;
import com.impltech.chatApp.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ValidationUtil.class);

    public static void checkUserExistenceById(final Boolean isPresent) {
        if (!isPresent) {
            throw new UserNotFoundException("User not found!");
        }
    }

    public static void checkUsernameValidity(final String username) {
        if (username == null || username.isEmpty()) {
            throw new InvalidUsernameException("Invalid or empty username!");
        }
    }

    public static void isUserAlreadyExists(final Boolean isPresent) {
        if (isPresent) {
            throw new UserAlreadyExistsException("User already exists!");
        }
    }

    public static void checkEmailValidity(final String email) {
        if (email == null || email.isEmpty()) {
            throw new InvalidEmailException("Invalid or empty email!");
        }
    }

    public static void isUserExistsByUsername(final Boolean isPresent) {
        if (!isPresent) {
            throw new UserNotFoundException("User with such name not exists!");
        }
    }
}
