package com.impltech.chatApp.dto;

public class NotificationDto {
    private String type;
    private String content;
    private Long fromUserId;

    public NotificationDto() {
    }

    public NotificationDto(String type, String content, Long fromUserId) {
        this.type = type;
        this.content = content;
        this.fromUserId = fromUserId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }
}
