package com.impltech.chatApp.exceptions;

public class ChatRoomNotExistsException extends RuntimeException {
    public ChatRoomNotExistsException(String message) {
        super(message);
    }
}
