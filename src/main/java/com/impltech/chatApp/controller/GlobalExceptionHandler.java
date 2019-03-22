package com.impltech.chatApp.controller;

import com.impltech.chatApp.dto.ApiErrorResponse;
import com.impltech.chatApp.exceptions.ChatRoomNotExistsException;
import com.impltech.chatApp.exceptions.InvalidEmailException;
import com.impltech.chatApp.exceptions.InvalidUsernameException;
import com.impltech.chatApp.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = ChatRoomNotExistsException.class)
    public ApiErrorResponse handleChatRoomNotExistsException(final ChatRoomNotExistsException ex) {
        return getApiErrorResponse(ex.getClass().getSimpleName(), ex.getCause(), ex.getMessage());
    }

    @ExceptionHandler(value = InvalidEmailException.class)
    public ApiErrorResponse handleInvalidEmailException(final InvalidEmailException ex) {
        return getApiErrorResponse(ex.getClass().getSimpleName(), ex.getCause(), ex.getMessage());
    }

    @ExceptionHandler(value = InvalidUsernameException.class)
    public ApiErrorResponse handleInvalidUsernameException(final InvalidUsernameException ex) {
        return getApiErrorResponse(ex.getClass().getSimpleName(), ex.getCause(), ex.getMessage());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ApiErrorResponse handleUserNotFoundException(final UserNotFoundException ex) {
        return getApiErrorResponse(ex.getClass().getSimpleName(), ex.getCause(), ex.getMessage());
    }

    private ApiErrorResponse getApiErrorResponse(final String errorName, final Throwable cause, final String errMsg) {
        final String errCause = cause.toString();
        LOG.error(errCause);
        return new ApiErrorResponse(errorName, errCause, errMsg);
    }
}
