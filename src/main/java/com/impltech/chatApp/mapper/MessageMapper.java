package com.impltech.chatApp.mapper;

import com.impltech.chatApp.dto.MessageDto;
import com.impltech.chatApp.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper implements BaseMapper<Message, MessageDto> {
    @Override
    public MessageDto toDto(final Message entity) {
        return null;
    }

    @Override
    public Message toEntity(final MessageDto dto) {
        return null;
    }
}
