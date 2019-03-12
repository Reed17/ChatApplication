package com.impltech.chatApp.repository;

import com.impltech.chatApp.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, Long> {
    List<Message> findMessagesByUsername(String username, String chatRoomId);
}
