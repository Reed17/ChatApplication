package com.impltech.chatApp.dto;

import com.impltech.chatApp.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomDto {
    private Long chatRoomId;
    private String name;
    private List<User> users = new ArrayList<>();

    public ChatRoomDto() {
    }

    public ChatRoomDto(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }

    public ChatRoomDto(Long chatRoomId, String name, List<User> users) {
        this.chatRoomId = chatRoomId;
        this.name = name;
        this.users = users;
    }

    public Long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
