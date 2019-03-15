package com.impltech.chatApp.enums;

public enum Role {
    USER("USER"), MANAGER("MANAGER");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
