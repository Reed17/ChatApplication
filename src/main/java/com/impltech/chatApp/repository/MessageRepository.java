package com.impltech.chatApp.repository;

import com.impltech.chatApp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // TODO
    //  1) get existed messages for 2 users
}
