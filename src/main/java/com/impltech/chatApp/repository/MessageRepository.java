package com.impltech.chatApp.repository;

import com.impltech.chatApp.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findMessageByUsernameAndChatRoomId(final String username, final String chatRoomId);
}
