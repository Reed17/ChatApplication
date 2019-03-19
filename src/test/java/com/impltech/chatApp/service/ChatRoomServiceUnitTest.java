package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.ChatRoomDto;
import com.impltech.chatApp.entity.ChatRoom;
import com.impltech.chatApp.entity.Message;
import com.impltech.chatApp.entity.User;
import com.impltech.chatApp.mapper.ChatRoomMapper;
import com.impltech.chatApp.mapper.UserMapper;
import com.impltech.chatApp.repository.ChatRoomRepository;
import com.impltech.chatApp.service.impl.RedisChatRoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ChatRoomServiceUnitTest {

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @Mock
    private MessageService messageService;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private ChatRoomMapper chatRoomMapper;

    @Mock
    private UserMapper userMapper;

    @Captor
    private ArgumentCaptor<ChatRoom> chatRoomCaptor;

    @Captor
    private ArgumentCaptor<Message> messageCaptor;

    @Captor
    private ArgumentCaptor<String> toUserCaptor;

    @Captor
    private ArgumentCaptor<String> destinationCaptor;

    @Captor
    private ArgumentCaptor<Object> messageObjectCaptor;

    @InjectMocks
    private ChatRoomService chatRoomService = new RedisChatRoomServiceImpl();

    @Test
    void whenCreateChatRoomThenOperationSuccessful() {
        ChatRoom newChatRoom = new ChatRoom("user problem solving chat", Collections.emptyList());
        ChatRoomDto dto = new ChatRoomDto("user problem solving chat", Collections.emptyList());

        when(chatRoomMapper.toEntity(dto)).thenReturn(newChatRoom);
        when(chatRoomRepository.save(newChatRoom)).thenAnswer(new Answer<ChatRoom>() {
            @Override
            public ChatRoom answer(InvocationOnMock invocation) throws Throwable {
                ChatRoom room = invocation.getArgument(0);
                room.setChatRoomId("1ewsdsc123");
                return room;
            }
        });
        //when(chatRoomMapper.toDto(newChatRoom)).thenReturn(dto);

        chatRoomService.save(dto);
        Mockito.verify(chatRoomRepository, Mockito.times(1)).save(newChatRoom);
    }

    @Test
    void whenGetChatRoomByIdThenOperationSuccessful() {
        ChatRoom room = new ChatRoom("test room", Collections.emptyList());
        room.setChatRoomId("1qaz");

        when(chatRoomRepository.findById(anyString())).thenReturn(Optional.of(room));

        chatRoomService.getById(room.getChatRoomId());
        Mockito.verify(chatRoomRepository, times(1)).findById(room.getChatRoomId());
    }

    @Test
    void whenGetAllExistedRoomsThenOperationIsSuccessful() {
        chatRoomService.getAllChatRoomsList();
        Mockito.verify(chatRoomRepository, times(1)).findAll();
    }
}
