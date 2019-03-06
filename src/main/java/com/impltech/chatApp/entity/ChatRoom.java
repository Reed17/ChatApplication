package com.impltech.chatApp.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @OneToOne(cascade = CascadeType.MERGE)
    private User manager;

    @OneToOne(cascade = CascadeType.MERGE)
    private User client;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> roomMessages = new ArrayList<>();

    public ChatRoom() {
    }

    public ChatRoom(User manager, User client) {
        this.manager = manager;
        this.client = client;
    }

    public ChatRoom(User manager, User client, List<Message> roomMessages) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return Objects.equals(roomId, chatRoom.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId);
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "roomId=" + roomId +
                ", manager=" + manager +
                ", client=" + client +
                ", roomMessages=" + roomMessages +
                '}';
    }
}
