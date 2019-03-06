package com.impltech.chatApp.mapper;

import com.impltech.chatApp.dto.ChatRoomDto;
import com.impltech.chatApp.entity.ChatRoom;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomMapper implements BaseMapper<ChatRoom, ChatRoomDto> {
    @Override
    public ChatRoomDto toDto(final ChatRoom entity) {
        return new ChatRoomDto(entity.getRoomId(), entity.getManager(), entity.getClient(), entity.getRoomMessages());
    }

    @Override
    public ChatRoom toEntity(final ChatRoomDto dto) {
        final ChatRoom chatRoom = new ChatRoom(dto.getManager(), dto.getClient(), dto.getRoomMessages());
        chatRoom.setRoomId(dto.getRoomId());
        return chatRoom;
    }
}
