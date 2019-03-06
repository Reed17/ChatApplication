package com.impltech.chatApp.repository;

import com.impltech.chatApp.dto.ChatRoomDto;
import com.impltech.chatApp.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    // TODO
    //  3) get free manager rooms

    /*@Query(value = "SELECT " +
            "NEW com.impltech.chatApp.dto.ChatRoomDto(cr.roomId, cr.manager, cr.client, cr.roomMessages) " +
            "FROM ChatRoom cr " +
            "WHERE cr.manager.userId = :managerId " +
            "AND cr.client.userId = :clientId")
    ChatRoomDto getChatRoomByTwoUsers(@Param("managerId") final Long managerId,
                                      @Param("clientId") final Long clientId);*/

}
