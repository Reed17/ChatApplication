package com.impltech.chatApp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RedisHash("chatrooms")
public class ChatRoom {

    @Id
    private String chatRoomId;

    private String name;

    private List<User> connectedUsers = new ArrayList<>();

    public ChatRoom() {
    }

    public ChatRoom(String name, List<User> connectedUsers) {
        this.name = name;
        this.connectedUsers = connectedUsers;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(List<User> connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return Objects.equals(chatRoomId, chatRoom.chatRoomId) &&
                Objects.equals(name, chatRoom.name) &&
                Objects.equals(connectedUsers, chatRoom.connectedUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatRoomId, name, connectedUsers);
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "chatRoomId=" + chatRoomId +
                ", name='" + name + '\'' +
                ", connectedUsers=" + connectedUsers +
                '}';
    }
}
