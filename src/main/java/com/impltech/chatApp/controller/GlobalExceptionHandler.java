package com.impltech.chatApp.controller;

import com.impltech.chatApp.dto.ApiErrorResponse;
import com.impltech.chatApp.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = ChatRoomNotExistsException.class)
    @ResponseBody
    public ApiErrorResponse handleChatRoomNotExistsException(final ChatRoomNotExistsException ex) {
        return getApiErrorResponse(ex.getClass().getSimpleName(), ex.getCause(), ex.getMessage());
    }

    @ExceptionHandler(value = InvalidEmailException.class)
    @ResponseBody
    public ApiErrorResponse handleInvalidEmailException(final InvalidEmailException ex) {
        return getApiErrorResponse(ex.getClass().getSimpleName(), ex.getCause(), ex.getMessage());
    }

    @ExceptionHandler(value = InvalidUsernameException.class)
    @ResponseBody
    public ApiErrorResponse handleInvalidUsernameException(final InvalidUsernameException ex) {
        return getApiErrorResponse(ex.getClass().getSimpleName(), ex.getCause(), ex.getMessage());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseBody
    public ApiErrorResponse handleUserNotFoundException(final UserNotFoundException ex) {
        return getApiErrorResponse(ex.getClass().getSimpleName(), ex.getCause(), ex.getMessage());
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    @ResponseBody
    public ApiErrorResponse handleUserAlreadyExistsException(final UserAlreadyExistsException ex) {
        return getApiErrorResponse(ex.getClass().getSimpleName(), ex.getCause(), ex.getMessage());
    }

    private ApiErrorResponse getApiErrorResponse(final String errorName, final Throwable cause, final String errMsg) {
        final String errCause = String.valueOf(cause);
        LOG.error(errCause);
        return new ApiErrorResponse(errorName, errCause, errMsg);
    }
}
