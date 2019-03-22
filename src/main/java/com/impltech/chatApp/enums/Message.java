package com.impltech.chatApp.enums;

public enum Message {
    ;

    private final String message;

    Message(final String msg) {
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }
}
