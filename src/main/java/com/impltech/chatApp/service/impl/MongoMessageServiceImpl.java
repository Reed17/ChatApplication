package com.impltech.chatApp.service.impl;

import com.impltech.chatApp.entity.Message;
import com.impltech.chatApp.repository.MessageRepository;
import com.impltech.chatApp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoMessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MongoMessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void sendMessageToConversation(Message message) {
        setUsernameToMessageObject(message, message.getFromUser());
        saveMessage(message);
        setUsernameToMessageObject(message, message.getToUser());
        saveMessage(message);
    }

    @Override
    public List<Message> findMessageHistoryFor(String userName, String chatRoomId) {
        return messageRepository.findMessageByUsernameAndChatRoomId(userName, chatRoomId);
    }

    private Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    private void setUsernameToMessageObject(Message message, String fromUser) {
        message.setUsername(fromUser);
    }
}
