package com.impltech.chatApp.controller;

import com.impltech.chatApp.dto.MessageDto;
import com.impltech.chatApp.service.MessageService;
import com.impltech.chatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private UserService userService;
    private MessageService messageService;

    @Autowired
    public ChatController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @MessageMapping("/chats/{chatRoomId}")
    @SendTo("/topic/chats")
    public ResponseEntity<?> handleChat(@Payload MessageDto messageDto,
                                                        @DestinationVariable("roomId") final Long chatRoomId) {
        messageService.sendMessage(chatRoomId, messageDto);

        return ResponseEntity.ok().body(String.format("To room %d sent message {%s}", chatRoomId, messageDto));
    }
}
