package com.impltech.chatApp.utils;

import com.impltech.chatApp.enums.Message;
import com.impltech.chatApp.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ValidationUtil.class);

    public static void checkUserExistenceById(final Boolean isPresent) throws Throwable {
        if (!isPresent) {
            final String errMsg = Message.USER_NOT_FOUND.getMessage();
            doThrowingAction(errMsg, new UserNotFoundException(errMsg));
        }
    }

    public static void checkUsernameValidity(final String username) throws Throwable {
        if (username == null || username.isEmpty()) {
            final String errMsg = Message.INVALID_OR_EMPTY_USERNAME.getMessage();
            doThrowingAction(errMsg, new InvalidUsernameException(errMsg));
        }
    }

    public static void checkEmailValidity(final String email) throws Throwable {
        if (email == null || email.isEmpty()) {
            final String errMsg = Message.INVALID_EMAIL.getMessage();
            doThrowingAction(errMsg, new InvalidEmailException(errMsg));
        }
    }

    public static void isUserExistsByUsername(final Boolean isPresent) throws Throwable {
        if (!isPresent) {
            final String errMsg = Message.USER_WITH_SUCH_NAME_NOT_EXISTS.getMessage();
            doThrowingAction(errMsg, new UserNotFoundException(errMsg));
        }
    }

    public static void isMessageEmpty(final String message) throws Throwable {
        if (message == null || message.isEmpty()) {
            final String errMsg = Message.EMPTY_MESSAGE.getMessage();
            doThrowingAction(errMsg, new EmptyMessageException(errMsg));
        }
    }

    public static void isChatRoomExists(final Boolean isPresent) throws Throwable {
        if (!isPresent) {
            final String errMsg = Message.CHAT_ROOM_NOT_EXISTS.getMessage();
            doThrowingAction(errMsg, new ChatRoomNotExistsException(errMsg));
        }
    }

    private static void doThrowingAction(final String errMsg, final Throwable throwable) throws Throwable {
        LOG.error(errMsg);
        throw throwable;
    }
}
