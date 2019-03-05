package com.impltech.chatApp.dto;

import com.impltech.chatApp.entity.User;

public class ChatRoomDto {
    private Long roomId;
    private User manager;
    private User client;

    public ChatRoomDto() {
    }

    public ChatRoomDto(Long roomId, User manager, User client) {
        this.roomId = roomId;
        this.manager = manager;
        this.client = client;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }
}
