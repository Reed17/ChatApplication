package com.impltech.chatApp.dto;

import com.impltech.chatApp.entity.User;

public class MessageDto {
    private Long messageId;
    private String content;
    private User from;
    private User to;

    public MessageDto() {
    }

    public MessageDto(Long messageId, String content, User from, User to) {
        this.messageId = messageId;
        this.content = content;
        this.from = from;
        this.to = to;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }
}
