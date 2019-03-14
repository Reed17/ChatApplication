package com.impltech.chatApp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document(collection = "messages")
public class Message {

    @Id
    private String chatRoomId;

    private String username;

    private Date date;

    private String fromUser;

    private String toUser;

    private String content;

    public Message() {
    }

    public Message(Date date, String fromUser, String toUser, String content) {
        this.date = date;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
    }

    public Message(String username, Date date, String fromUser, String toUser, String content) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(chatRoomId, message.chatRoomId) &&
                Objects.equals(username, message.username) &&
                Objects.equals(date, message.date) &&
                Objects.equals(fromUser, message.fromUser) &&
                Objects.equals(toUser, message.toUser) &&
                Objects.equals(content, message.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatRoomId, username, date, fromUser, toUser, content);
    }

    @Override
    public String toString() {
        return "Message{" +
                "chatRoomId=" + chatRoomId +
                ", username='" + username + '\'' +
                ", date=" + date +
                ", fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
