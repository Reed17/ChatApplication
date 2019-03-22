package com.impltech.chatApp.service;

import com.impltech.chatApp.entity.Message;

import java.util.List;

public interface MessageService {
    void sendMessageToConversation(final Message message) throws Throwable;
    List<Message> findMessageHistoryFor(final String userName, final String chatRoomId) throws Throwable;
}
