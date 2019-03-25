package com.impltech.chatApp.controller;

import com.impltech.chatApp.dto.ChatRoomDto;
import com.impltech.chatApp.dto.UserDto;
import com.impltech.chatApp.entity.Message;
import com.impltech.chatApp.entity.User;
import com.impltech.chatApp.service.ChatRoomService;
import com.impltech.chatApp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@PreAuthorize("hasAnyAuthority('USER')")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final MessageService messageService;

    @Autowired
    public ChatRoomController(final ChatRoomService chatRoomService, final MessageService messageService) {
        this.chatRoomService = chatRoomService;
        this.messageService = messageService;
    }

    @PostMapping("/chatroom/new")
    public ResponseEntity<?> createChatRoom(@RequestBody final ChatRoomDto chatRoomDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatRoomService.save(chatRoomDto));
    }

    @GetMapping("/chats")
    public ResponseEntity<?> getChatRooms() {
        return ResponseEntity
                .ok()
                .body(chatRoomService.getAllChatRoomsList());
    }

    @PutMapping("/chatroom/{chatRoomId}/join")
    public ResponseEntity<?> joinTheRoom(@PathVariable("chatRoomId") final String chatRoomId,
                                         @RequestBody final UserDto userDto) {
        return ResponseEntity
                .ok()
                .body(chatRoomService.join(userDto, chatRoomId));
    }

    @PutMapping("/chatroom/{chatRoomId}/leave")
    public ResponseEntity<?> leaveTheRoom(@PathVariable("chatRoomId") final String chatRoomId,
                                         @RequestBody final UserDto userDto) {
        return ResponseEntity
                .ok()
                .body(chatRoomService.leave(userDto, chatRoomId));
    }

    // TODO add response wrappers

    @SubscribeMapping("/connected.users")
    public List<User> listChatRoomConnectedUsersOnSubscribe(final SimpMessageHeaderAccessor headerAccessor) {
        final String chatRoomId = headerAccessor.getSessionAttributes().get("chatRoomId").toString();
        return chatRoomService.getById(chatRoomId).getConnectedUsers();
    }

    @SubscribeMapping("/old.messages")
    public List<Message> listOldMessagesFromUserOnSubscribe(
            final UserDto userDto,
            final SimpMessageHeaderAccessor headerAccessor) throws Throwable {
        final String chatRoomId = headerAccessor.getSessionAttributes().get("chatRoomId").toString();
        return messageService.findMessageHistoryFor(userDto.getUsername(), chatRoomId);
    }

    @MessageMapping("/send.message")
    public void sendMessage(@Payload final Message message, final UserDto userDto, final SimpMessageHeaderAccessor headerAccessor) throws Throwable {
        final String chatRoomId = headerAccessor.getSessionAttributes().get("chatRoomId").toString();
        message.setFromUser(userDto.getUsername());
        message.setChatRoomId(chatRoomId);
        messageService.sendMessageToConversation(message);
    }
}