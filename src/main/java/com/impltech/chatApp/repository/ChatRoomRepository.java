package com.impltech.chatApp.repository;

import com.impltech.chatApp.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // TODO
    //  1) get room by id
    //  2) get room by 2 users
    //  3) get free manager rooms
    //  4) get
}
