package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.ChatRoomDto;
import com.impltech.chatApp.dto.UserDto;
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

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
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

    @Test
    void whenUsersJoinChatRoomThenOperationIsSuccessful() {
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.setChatRoomId("1qaswed");
        chatRoomDto.setName("joining room");
        UserDto dto = new UserDto("test", "test@gmail.com", "1qazxsw2");

        assertThat(chatRoomDto.getUsers().size(), is(0));

        ChatRoom roomWithOneUser = new ChatRoom();
        roomWithOneUser.setChatRoomId("1qaswed");
        roomWithOneUser.setName("joining room");
        User joiningUser = new User("test", "test@gmail.com", "1qazxsw2");

        when(chatRoomRepository.findById(eq(roomWithOneUser.getChatRoomId()))).thenReturn(Optional.of(roomWithOneUser));
        when(chatRoomRepository.save(roomWithOneUser))
                .thenAnswer((Answer<ChatRoom>) invocation -> {
                    ChatRoom room = invocation.getArgument(0);
                    room.getConnectedUsers().add(0, joiningUser);
                    room.getConnectedUsers().remove(1); // hardcoded now users size = 1
                    return room;
                });

        chatRoomService.join(dto, chatRoomDto);

        assertThat(roomWithOneUser.getConnectedUsers().size(), is(1));
        verify(chatRoomRepository, times(1)).save(roomWithOneUser);
        verify(simpMessagingTemplate, times(1)).convertAndSend(
                destinationCaptor.capture(),
                messageObjectCaptor.capture());
        assertThat(roomWithOneUser.getConnectedUsers().contains(joiningUser), is(true));
    }
}
