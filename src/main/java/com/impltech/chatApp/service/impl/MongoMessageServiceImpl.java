package com.impltech.chatApp.service.impl;

import com.impltech.chatApp.entity.Message;
import com.impltech.chatApp.repository.MessageRepository;
import com.impltech.chatApp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoMessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MongoMessageServiceImpl(final MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void sendMessageToConversation(final Message message) {
        setUsernameToMessageObject(message, message.getFromUser());
        saveMessage(message);
        setUsernameToMessageObject(message, message.getToUser());
        saveMessage(message);
    }

    @Override
    public List<Message> findMessageHistoryFor(final String userName, final String chatRoomId) {
        return messageRepository.findMessageByUsernameAndChatRoomId(userName, chatRoomId);
    }

    private Message saveMessage(final Message message) {
        return messageRepository.save(message);
    }

    private void setUsernameToMessageObject(final Message message, final String fromUser) {
        message.setUsername(fromUser);
    }
}
