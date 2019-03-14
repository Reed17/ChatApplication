package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.ChatRoomDto;
import com.impltech.chatApp.dto.MessageDto;
import com.impltech.chatApp.dto.UserDto;
import com.impltech.chatApp.mapper.ChatRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisChatRoomServiceImpl implements ChatRoomService {

    private SimpMessagingTemplate simpMessagingTemplate;
    private ChatRoomMapper roomMapper;


    @Override
    public ChatRoomDto save(ChatRoomDto chatRoomDto) {
        return null;
    }

    @Override
    public ChatRoomDto getById(Long chatRoomId) {
        return null;
    }

    @Override
    public ChatRoomDto join(UserDto userDto, ChatRoomDto chatRoomDto) {
        return null;
    }

    @Override
    public ChatRoomDto leave(UserDto userDto, ChatRoomDto chatRoomDto) {
        return null;
    }

    @Override
    public void sendMessage(MessageDto messageDto) {

    }

    @Override
    public List<ChatRoomDto> getAllChatRoomsList() {
        return null;
    }
}
