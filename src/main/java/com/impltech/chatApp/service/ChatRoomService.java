package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.ChatRoomDto;
import com.impltech.chatApp.dto.MessageDto;
import com.impltech.chatApp.dto.UserDto;
import com.impltech.chatApp.entity.Message;

import java.util.List;

public interface ChatRoomService {
    ChatRoomDto save(ChatRoomDto chatRoomDto);
    ChatRoomDto getById(String chatRoomId);
    ChatRoomDto join(UserDto userDto, ChatRoomDto chatRoomDto);
    ChatRoomDto leave(UserDto userDto, ChatRoomDto chatRoomDto);
    void sendMessage(Message message);
    List<ChatRoomDto> getAllChatRoomsList();
}
