package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.ChatRoomDto;
import com.impltech.chatApp.dto.MessageDto;
import com.impltech.chatApp.dto.UserDto;
import com.impltech.chatApp.entity.Message;

import java.util.List;

public interface ChatRoomService {
    ChatRoomDto save(final ChatRoomDto chatRoomDto);
    ChatRoomDto getById(final String chatRoomId);
    ChatRoomDto join(final Long userId, final String chatRoomId) throws Throwable;
    ChatRoomDto leave(final Long userId, final String chatRoomId) throws Throwable;
    void sendMessage(final Message message) throws Throwable;
    List<ChatRoomDto> getAllChatRoomsList();
}
