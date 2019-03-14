package com.impltech.chatApp.service;

import com.impltech.chatApp.entity.Message;
import com.impltech.chatApp.mapper.MessageMapper;
import com.impltech.chatApp.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoMessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;
    private MessageMapper messageMapper;

    @Autowired
    public MongoMessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public void sendMessageToConversation(Message message) {
        message.setUsername(message.getFromUser());
        messageRepository.save(message);
        message.setUsername(message.getToUser());
        messageRepository.save(message);
    }

    @Override
    public List<Message> findMessageHistoryFor(String userName, String chatRoomId) {
        return messageRepository.findMessageByUsernameAndChatRoomId(userName, chatRoomId);
    }
}
