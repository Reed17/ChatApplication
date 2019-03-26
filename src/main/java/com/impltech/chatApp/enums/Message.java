package com.impltech.chatApp.enums;

public enum Message {
    USER_NOT_FOUND("User not found!"),
    INVALID_OR_EMPTY_USERNAME("Invalid or empty username!"),
    INVALID_EMAIL("Invalid or empty email!"),
    USER_WITH_SUCH_NAME_NOT_EXISTS("User with such name not exists!"),
    EMPTY_MESSAGE("Message shouldn't be empty!"),
    CHAT_ROOM_NOT_EXISTS("Chat room doesn't exists!"),
    USER_ALREADY_EXISTS("User with this email already exists!"),
    USER_WITH_SUCH_EMAIL_NOT_EXISTS("User with such email doesn't exists!"),
    UNAUTHORIZED_ACCESS("You are not authorized to access this resource!"),
    INVALID_TOKEN("Invalid access token!"),
    AUTHENTICATION_FAILED("Failed to authenticate user!");

    private final String message;

    Message(final String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }
}
