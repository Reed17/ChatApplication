package com.impltech.chatApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChatRoomNotExistsException extends RuntimeException {
    public ChatRoomNotExistsException(final String message) {
        super(message);
    }
}
