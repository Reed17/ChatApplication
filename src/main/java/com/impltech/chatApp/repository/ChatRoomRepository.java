package com.impltech.chatApp.repository;

import com.impltech.chatApp.entity.ChatRoom;
import org.springframework.data.repository.CrudRepository;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, Long> {
}
