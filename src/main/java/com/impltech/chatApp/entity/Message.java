package com.impltech.chatApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.Objects;

@Document(collection = "messages")
@TypeAlias(value = "message")
public class Message {

    @Id
    private Long messageId;

    private String username;

    private Date date;

    @Field(value = "from")
    private String fromUser;

    @Field(value = "to")
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

    @PersistenceConstructor
    public Message(String username, Date date, String fromUser, String toUser, String content) {
        this.username = username;
        this.date = date;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
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
        return Objects.equals(messageId, message.messageId) &&
                Objects.equals(username, message.username) &&
                Objects.equals(date, message.date) &&
                Objects.equals(fromUser, message.fromUser) &&
                Objects.equals(toUser, message.toUser) &&
                Objects.equals(content, message.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, username, date, fromUser, toUser, content);
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", username='" + username + '\'' +
                ", date=" + date +
                ", fromUser='" + fromUser + '\'' +
                ", toUser='" + toUser + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
