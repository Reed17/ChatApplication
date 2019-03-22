package com.impltech.chatApp.utils;

import com.impltech.chatApp.dto.UserDto;
import com.impltech.chatApp.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventHandler {

    private final ChatRoomService chatRoomService;

    @Autowired
    public WebSocketEventHandler(final ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @EventListener
    public void handleSessionConnectEvent(final SessionConnectEvent event) {
        final SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        final String chatRoomId = headers.getNativeHeader("chatRoomId").get(0);
        headers.getSessionAttributes().put("chatRoomId", chatRoomId);
        final UserDto user = new UserDto();
        user.setUsername(event.getUser().getName());

        chatRoomService.join(user, chatRoomId);
    }

    @EventListener
    public void handleSessionDisconnectEvent(final SessionDisconnectEvent event) {
        final SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        final String chatRoomId = headers.getSessionAttributes().get("chatRoomId").toString();
        final UserDto leavingUser = new UserDto();
        leavingUser.setUsername(event.getUser().getName());

        chatRoomService.leave(leavingUser, chatRoomId);
    }
}
