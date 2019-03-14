package com.impltech.chatApp.service;

import com.impltech.chatApp.entity.Message;

import java.util.List;

public interface MessageService {
    void sendMessageToConversation(Message message);
    List<Message> findMessageHistoryFor(String userName, String chatRoomId);
}
