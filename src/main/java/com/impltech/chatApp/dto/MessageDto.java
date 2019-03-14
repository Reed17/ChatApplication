package com.impltech.chatApp.dto;

import java.util.Date;

public class MessageDto {
    private String chatRoomId;
    private String username;
    private Date date;
    private String fromUser;
    private String toUser;
    private String content;

    public MessageDto() {
    }

    public MessageDto(Date date, String fromUser, String toUser, String content) {
        this.date = date;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
    }

    public MessageDto(String chatRoomId, Date date, String fromUser, String toUser, String content) {
        this.chatRoomId = chatRoomId;
        this.date = date;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
    }

    public MessageDto(String chatRoomId, String username, Date date, String fromUser, String toUser, String content) {
        this.chatRoomId = chatRoomId;
        this.username = username;
        this.date = date;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "chatRoomId=" + chatRoomId +
                ", date=" + date +
                ", fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

