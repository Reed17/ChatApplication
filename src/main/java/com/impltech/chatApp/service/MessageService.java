package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.MessageDto;

import java.util.List;

public interface MessageService {
    void sendMessageToConversation(MessageDto message);
    List<MessageDto> findMessageHistoryFor(String userName, Long chatRoomId);
}
