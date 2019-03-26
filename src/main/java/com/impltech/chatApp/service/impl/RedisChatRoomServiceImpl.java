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

import static com.impltech.chatApp.utils.ValidationUtil.isMessageContentEmpty;

@Service
public class RedisChatRoomServiceImpl implements ChatRoomService {

    private final SimpMessagingTemplate webSocketMessagingTemplate;
    private final MessageService messageService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper roomMapper;
    private final UserMapper userMapper;

    @Autowired
    public RedisChatRoomServiceImpl(
            final SimpMessagingTemplate webSocketMessagingTemplate,
            final MessageService messageService,
            final ChatRoomRepository chatRoomRepository,
            final ChatRoomMapper roomMapper,
            final UserMapper userMapper) {
        this.webSocketMessagingTemplate = webSocketMessagingTemplate;
        this.messageService = messageService;
        this.chatRoomRepository = chatRoomRepository;
        this.roomMapper = roomMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ChatRoomDto save(final ChatRoomDto chatRoomDto) {
        final ChatRoom chatRoom = saveChatRoom(roomMapper.toEntity(chatRoomDto));
        return getChatRoomDto(chatRoom);
    }

    @Override
    public ChatRoomDto getById(final String chatRoomId) {
        final Optional<ChatRoom> chatRoomWrapper = getByIdInternal(chatRoomId);
        return getChatRoomDto(getRoomFromWrapper(chatRoomWrapper));
    }

    @Override
    public ChatRoomDto join(final UserDto userDto, final String chatRoomId) {
        final User user = getUser(userDto);
        final Optional<ChatRoom> roomWrapper = getByIdInternal(chatRoomId);
        final ChatRoom room = getRoomFromWrapper(roomWrapper);

        room.getConnectedUsers().add(user);

        saveChatRoom(room);
        updateConnectedUsersViaWebSocket(room);
        return getChatRoomDto(room);
    }

    private ChatRoom getRoomFromWrapper(Optional<ChatRoom> roomWrapper) {
        return roomWrapper.get();
    }

    @Override
    public ChatRoomDto leave(final UserDto userDto, final String chatRoomId) {
        final User user = getUser(userDto);
        final Optional<ChatRoom> roomWrapper = getByIdInternal(chatRoomId);
        final ChatRoom room = getRoomFromWrapper(roomWrapper);

        room.getConnectedUsers().remove(user);

        saveChatRoom(room);
        updateConnectedUsersViaWebSocket(room);
        return getChatRoomDto(room);
    }

    @Override
    public void sendMessage(final Message message) throws Throwable {
        isMessageContentEmpty(message.getContent());
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
        // todo append this message to conversation
        messageService.sendMessageToConversation(message);
    }

    @Override
    public List<ChatRoomDto> getAllChatRoomsList() {
        final List<ChatRoom> chatRooms = (List<ChatRoom>) chatRoomRepository.findAll();
        return makeChatRoomList(chatRooms);
    }

    private Optional<ChatRoom> getByIdInternal(final String chatRoomId) {
        return chatRoomRepository.findById(chatRoomId);
    }

    private User getUser(final UserDto userDto) {
        return userMapper.toEntity(userDto);
    }

    private ChatRoom saveChatRoom(final ChatRoom room) {
        return chatRoomRepository.save(room);
    }

    private ChatRoomDto getChatRoomDto(final ChatRoom room) {
        return roomMapper.toDto(room);
    }

    private void updateConnectedUsersViaWebSocket(final ChatRoom chatRoom) {
        webSocketMessagingTemplate.convertAndSend(
                DestinationUtil.chatRoomConnectedUsers(chatRoom.getChatRoomId()),
                chatRoom.getConnectedUsers());
    }

    private List<ChatRoomDto> makeChatRoomList(final List<ChatRoom> chatRooms) {
        List<ChatRoomDto> dtoList = new ArrayList<>();
        chatRooms.forEach(room -> {
            dtoList.add(new ChatRoomDto(
                    room.getChatRoomId(), room.getName(), room.getConnectedUsers()
            ));
        });
        return dtoList;
    }
}
