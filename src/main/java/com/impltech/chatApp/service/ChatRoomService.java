package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.ChatRoomDto;

public interface ChatRoomService {
    ChatRoomDto saveNewRoom(final ChatRoomDto chatRoomDto);

    ChatRoomDto getRoomById(final Long chatRoomId);
}
