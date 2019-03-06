package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.ChatRoomDto;
import com.impltech.chatApp.entity.ChatRoom;
import com.impltech.chatApp.exceptions.ChatRoomNotExistsException;
import com.impltech.chatApp.mapper.ChatRoomMapper;
import com.impltech.chatApp.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private ChatRoomRepository chatRoomRepository;
    private ChatRoomMapper roomMapper;

    @Autowired
    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, ChatRoomMapper roomMapper) {
        this.chatRoomRepository = chatRoomRepository;
        this.roomMapper = roomMapper;
    }

    @Transactional
    @Override
    public ChatRoomDto saveNewRoom(ChatRoomDto chatRoomDto) {
        ChatRoom newChatRoom = chatRoomRepository.save(roomMapper.toEntity(chatRoomDto));
        return roomMapper.toDto(newChatRoom);
    }

    @Override
    public ChatRoomDto getRoomById(Long chatRoomId) {
        if (!chatRoomRepository.existsById(chatRoomId)) {
            throw new ChatRoomNotExistsException(String.format("Chat room id %d doesn`t exists!", chatRoomId));
        }
        ChatRoom chatRoom = chatRoomRepository.getOne(chatRoomId);
        return roomMapper.toDto(chatRoom);
    }
}
