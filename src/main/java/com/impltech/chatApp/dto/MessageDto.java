package com.impltech.chatApp.dto;

public class MessageDto {
    private Long messageId;
    private String content;

    public MessageDto() {
    }

    public MessageDto(String content) {
        this.content = content;
    }

    public MessageDto(Long messageId, String content) {
        this.messageId = messageId;
        this.content = content;
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

    @Override
    public String toString() {
        return "MessageDto{" +
                "messageId=" + messageId +
                ", content='" + content + '\'' +
                '}';
    }
}

