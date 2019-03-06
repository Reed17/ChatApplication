package com.impltech.chatApp.repository;

import com.impltech.chatApp.dto.MessageDto;
import com.impltech.chatApp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /*@Query(value = "SELECT " +
            "NEW com.impltech.chatApp.dto.MessageDto(msg.messageId, msg.from, msg.to, msg.content) " +
            "FROM Message msg " +
            "WHERE msg.from.userId IN (:managerId, :clientId) " +
            "AND msg.to.userId IN (:managerId, :clientId)")
    List<MessageDto> getMessagesByTwoUsers(@Param("managerId") final Long managerId,
                                           @Param("clientId") final Long clientId);*/
}
