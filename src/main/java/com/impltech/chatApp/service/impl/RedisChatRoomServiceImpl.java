package com.impltech.chatApp.service.impl;

import com.impltech.chatApp.dto.ChatRoomDto;
import com.impltech.chatApp.dto.UserDto;
import com.impltech.chatApp.entity.ChatRoom;
import com.impltech.chatApp.entity.Message;
import com.impltech.chatApp.entity.User;
import com.impltech.chatApp.mapper.ChatRoomMapper;
import com.impltech.chatApp.mapper.UserMapper;
import com.impltech.chatApp.repository.ChatRoomRepository;
import com.impltech.chatApp.service.ChatRoomService;
import com.impltech.chatApp.service.MessageService;
import com.impltech.chatApp.utils.DestinationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RedisChatRoomServiceImpl implements ChatRoomService {

    private SimpMessagingTemplate webSocketMessagingTemplate;
    private MessageService messageService;
    private ChatRoomRepository chatRoomRepository;
    private ChatRoomMapper roomMapper;
    private UserMapper userMapper;

    public RedisChatRoomServiceImpl() {
    }

    @Autowired
    public RedisChatRoomServiceImpl(
            SimpMessagingTemplate webSocketMessagingTemplate,
            MessageService messageService,
            ChatRoomRepository chatRoomRepository,
            ChatRoomMapper roomMapper,
            UserMapper userMapper) {
        this.webSocketMessagingTemplate = webSocketMessagingTemplate;
        this.messageService = messageService;
        this.chatRoomRepository = chatRoomRepository;
        this.roomMapper = roomMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ChatRoomDto save(ChatRoomDto chatRoomDto) {
        final ChatRoom chatRoom = saveChatRoom(roomMapper.toEntity(chatRoomDto));
        return getChatRoomDto(chatRoom);
    }

    @Override
    public ChatRoomDto getById(String chatRoomId) {
        final Optional<ChatRoom> chatRoomWrapper = getByIdInternal(chatRoomId);
        return getChatRoomDto(chatRoomWrapper.get());
    }

    @Override
    public ChatRoomDto join(UserDto userDto, ChatRoomDto chatRoomDto) {
        User user = getUser(userDto);
        Optional<ChatRoom> roomWrapper = getByIdInternal(chatRoomDto.getChatRoomId());
        final ChatRoom room = roomWrapper.get();

        room.getConnectedUsers().add(user);

        saveChatRoom(room);
        updateConnectedUsersViaWebSocket(room);
        return getChatRoomDto(room);
    }

    @Override
    public ChatRoomDto leave(UserDto userDto, ChatRoomDto chatRoomDto) {
        User user = getUser(userDto);
        Optional<ChatRoom> roomWrapper = getByIdInternal(chatRoomDto.getChatRoomId());
        final ChatRoom room = roomWrapper.get();

        room.getConnectedUsers().remove(user);

        saveChatRoom(room);
        updateConnectedUsersViaWebSocket(room);
        return getChatRoomDto(room);
    }

    @Override
    public void sendMessage(Message message) {
        // todo send message to user
        webSocketMessagingTemplate.convertAndSendToUser(
                message.getToUser(),
                DestinationUtil.chatRoomMessages(message.getChatRoomId()),
                message
        );
        // todo send message from user
        webSocketMessagingTemplate.convertAndSendToUser(
                message.getFromUser(),
                DestinationUtil.chatRoomMessages(message.getChatRoomId()),
                message);
    }

    @Override
    public List<ChatRoomDto> getAllChatRoomsList() {
        List<ChatRoom> chatRooms = (List<ChatRoom>) chatRoomRepository.findAll();
        return makeChatRoomList(chatRooms);
    }

    private Optional<ChatRoom> getByIdInternal(String chatRoomId) {
        return chatRoomRepository.findById(chatRoomId);
    }

    private User getUser(UserDto userDto) {
        return userMapper.toEntity(userDto);
    }

    private ChatRoom saveChatRoom(ChatRoom room) {
        return chatRoomRepository.save(room);
    }

    private ChatRoomDto getChatRoomDto(ChatRoom room) {
        return roomMapper.toDto(room);
    }

    private void updateConnectedUsersViaWebSocket(ChatRoom chatRoom) {
        webSocketMessagingTemplate.convertAndSend(
                DestinationUtil.chatRoomConnectedUsers(chatRoom.getChatRoomId()),
                chatRoom.getConnectedUsers());
    }

    private List<ChatRoomDto> makeChatRoomList(List<ChatRoom> chatRooms) {
        List<ChatRoomDto> dtoList = new ArrayList<>();
        chatRooms.forEach(room -> {
            dtoList.add(new ChatRoomDto(
                    room.getChatRoomId(), room.getName(), room.getConnectedUsers()
            ));
        });
        return dtoList;
    }
}
