package com.impltech.chatApp.mapper;

import com.impltech.chatApp.dto.MessageDto;
import com.impltech.chatApp.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper implements BaseMapper<Message, MessageDto> {
    @Override
    public MessageDto toDto(final Message entity) {
        final MessageDto dto = new MessageDto(
                entity.getChatRoomId(),
                entity.getDate(),
                entity.getFromUser(),
                entity.getToUser(),
                entity.getContent());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    @Override
    public Message toEntity(final MessageDto dto) {
        final Message msg = new Message(
                dto.getDate(),
                dto.getFromUser(),
                dto.getToUser(),
                dto.getContent()
        );
        msg.setUsername(dto.getUsername());
        msg.setChatRoomId(dto.getChatRoomId());
        return msg;
    }
}
