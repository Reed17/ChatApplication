package com.impltech.chatApp.mapper;

import com.impltech.chatApp.dto.MessageDto;
import com.impltech.chatApp.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper implements BaseMapper<Message, MessageDto> {
    @Override
    public MessageDto toDto(final Message entity) {
        return new MessageDto(entity.getMessageId(), entity.getContent(), entity.getFrom(), entity.getTo());
    }

    @Override
    public Message toEntity(final MessageDto dto) {
        final Message msg = new Message(dto.getContent(), dto.getFrom(), dto.getTo());
        msg.setMessageId(dto.getMessageId());
        return msg;
    }
}
