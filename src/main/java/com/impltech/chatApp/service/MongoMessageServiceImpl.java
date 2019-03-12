package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.MessageDto;
import com.impltech.chatApp.mapper.ChatRoomMapper;
import com.impltech.chatApp.mapper.MessageMapper;
import com.impltech.chatApp.mapper.UserMapper;
import com.impltech.chatApp.repository.ChatRoomRepository;
import com.impltech.chatApp.repository.MessageRepository;
import com.impltech.chatApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoMessageServiceImpl implements MessageService {

    private SimpMessagingTemplate simpMessagingTemplate;
    private UserRepository userRepository;
    private MessageRepository messageRepository;
    private ChatRoomRepository chatRoomRepository;
    private UserMapper userMapper;
    private MessageMapper messageMapper;
    private ChatRoomMapper chatRoomMapper;

    @Autowired
    public MongoMessageServiceImpl(SimpMessagingTemplate simpMessagingTemplate,
                                   UserRepository userRepository,
                                   MessageRepository messageRepository,
                                   ChatRoomRepository chatRoomRepository,
                                   UserMapper userMapper,
                                   MessageMapper messageMapper,
                                   ChatRoomMapper chatRoomMapper) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.userMapper = userMapper;
        this.messageMapper = messageMapper;
        this.chatRoomMapper = chatRoomMapper;
    }


    @Override
    public void sendMessageToConversation(MessageDto message) {

    }

    @Override
    public List<MessageDto> findMessageHistoryFor(String userName, Long chatRoomId) {
        return null;
    }
}
