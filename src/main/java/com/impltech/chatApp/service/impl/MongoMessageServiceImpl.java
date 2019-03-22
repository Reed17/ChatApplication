package com.impltech.chatApp.service.impl;

import com.impltech.chatApp.entity.Message;
import com.impltech.chatApp.repository.MessageRepository;
import com.impltech.chatApp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.impltech.chatApp.utils.ValidationUtil.*;

@Service
public class MongoMessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MongoMessageServiceImpl(final MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // todo is it better when value will return?
    @Override
    public void sendMessageToConversation(final Message message) throws Throwable {
        isMessageEmpty(message.getContent());

        setUsernameToMessageObject(message, message.getFromUser());
        saveMessage(message);

        setUsernameToMessageObject(message, message.getToUser());
        saveMessage(message);
    }

    @Override
    public List<Message> findMessageHistoryFor(final String userName, final String chatRoomId) throws Throwable {
        checkUsernameValidity(userName);
        return messageRepository.findMessageByUsernameAndChatRoomId(userName, chatRoomId);
    }

    private Message saveMessage(final Message message) throws Throwable {
        isMessageEmpty(message.getContent());
        return messageRepository.save(message);
    }

    private void setUsernameToMessageObject(final Message message, final String fromUser) {
        message.setUsername(fromUser);
    }
}
