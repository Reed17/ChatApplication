package com.impltech.chatApp.controller;

import com.impltech.chatApp.dto.MessageDto;
import com.impltech.chatApp.service.MessageService;
import com.impltech.chatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private UserService userService;
    private MessageService messageService;

    @Autowired
    public MessageController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @MessageMapping("/chats")
    @SendTo("/topic/chats")
    public MessageDto handleChat(final String message) {
        //messageService.sendMessage(/*chatRoomId, */messageDto);
        MessageDto messageDto = new MessageDto(message);
        System.out.println("received message: " + messageDto);
        return messageDto;
    }
}
