package com.impltech.chatApp.dto;

import com.impltech.chatApp.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomDto {
    private String chatRoomId;
    private String name;
    private List<User> connectedUsers = new ArrayList<>();

    public ChatRoomDto() {
    }

    public ChatRoomDto(String name, List<User> connectedUsers) {
        this.name = name;
        this.connectedUsers = connectedUsers;
    }

    public ChatRoomDto(String chatRoomId, String name, List<User> connectedUsers) {
        this.chatRoomId = chatRoomId;
        this.name = name;
        this.connectedUsers = connectedUsers;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public List<User> getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(List<User> connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChatRoomDto{" +
                "chatRoomId='" + chatRoomId + '\'' +
                ", name='" + name + '\'' +
                ", connectedUsers=" + connectedUsers +
                '}';
    }
}
