package com.impltech.chatApp.controller;

import com.impltech.chatApp.dto.ChatRoomDto;
import com.impltech.chatApp.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
public class ChatRoomController {

    private ChatRoomService chatRoomService;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @PostMapping
    public ResponseEntity<?> createNewRoom(@RequestBody final ChatRoomDto chatRoomDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatRoomService.saveNewRoom(chatRoomDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoom(@PathVariable("id") final Long chatRoomId) {
        return ResponseEntity.ok().body(chatRoomService.getRoomById(chatRoomId));
    }
}
