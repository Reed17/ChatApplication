package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.MessageDto;

public interface MessageService {
    void sendMessage(Long chatRoomId, MessageDto message);
}
