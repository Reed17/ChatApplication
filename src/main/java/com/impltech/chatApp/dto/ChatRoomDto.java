package com.impltech.chatApp.dto;

import com.impltech.chatApp.entity.Message;
import com.impltech.chatApp.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomDto {
    private Long roomId;
    private User manager;
    private User client;
    private List<Message> roomMessages = new ArrayList<>();

    public ChatRoomDto() {
    }

    public ChatRoomDto(User manager, User client, List<Message> roomMessages) {
        this.manager = manager;
        this.client = client;
        this.roomMessages = roomMessages;
    }

    public ChatRoomDto(Long roomId, User manager, User client, List<Message> roomMessages) {
        this.roomId = roomId;
        this.manager = manager;
        this.client = client;
        this.roomMessages = roomMessages;
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

    public List<Message> getRoomMessages() {
        return roomMessages;
    }

    public void setRoomMessages(List<Message> roomMessages) {
        this.roomMessages = roomMessages;
    }
}
