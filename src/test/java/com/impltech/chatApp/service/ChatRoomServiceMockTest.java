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
import com.impltech.chatApp.utils.DestinationUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ChatRoomServiceMockTest {

    @Mock
    private SimpMessagingTemplate simpMessagingTemplateMock;

    @Mock
    private MessageService messageServiceMock;

    @Mock
    private ChatRoomRepository chatRoomRepositoryMock;

    @Mock
    private ChatRoomMapper chatRoomMapperMock;

    @Mock
    private UserMapper userMapperMock;

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
    private RedisChatRoomServiceImpl chatRoomService;

    @Test
    void whenCreateChatRoomThenOperationSuccessful() {
        ChatRoom newChatRoom = new ChatRoom("user problem solving chat", Collections.emptyList());
        ChatRoomDto dto = new ChatRoomDto("user problem solving chat", Collections.emptyList());

        when(chatRoomMapperMock.toEntity(dto)).thenReturn(newChatRoom);
        when(chatRoomRepositoryMock.save(newChatRoom)).thenAnswer((Answer<ChatRoom>) invocation -> {
            ChatRoom room = invocation.getArgument(0);
            room.setChatRoomId("1ewsdsc123");
            return room;
        });

        chatRoomService.save(dto);
        Mockito.verify(chatRoomRepositoryMock, Mockito.times(1)).save(newChatRoom);
    }

    @Test
    void whenGetChatRoomByIdThenOperationSuccessful() {
        ChatRoom room = new ChatRoom("test room", Collections.emptyList());
        room.setChatRoomId("1qaz");
        when(chatRoomRepositoryMock.findById(anyString())).thenReturn(Optional.of(room));

        chatRoomService.getById(room.getChatRoomId());
        Mockito.verify(chatRoomRepositoryMock, times(1)).findById(room.getChatRoomId());
    }

    @Test
    void whenGetAllExistedRoomsThenOperationIsSuccessful() {
        chatRoomService.getAllChatRoomsList();
        Mockito.verify(chatRoomRepositoryMock, times(1)).findAll();
    }

    @Test
    void whenUsersJoinChatRoomThenOperationIsSuccessful() {
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.setChatRoomId("1qaswed");
        chatRoomDto.setName("joining room");
        UserDto dto = new UserDto("test", "test@gmail.com", "1qazxsw2");

        assertThat(chatRoomDto.getConnectedUsers().size(), is(0));

        ChatRoom roomWithOneUser = new ChatRoom();
        roomWithOneUser.setChatRoomId("1qaswed");
        roomWithOneUser.setName("joining room");
        User joiningUser = new User("test", "test@gmail.com", "1qazxsw2");

        when(chatRoomRepositoryMock.findById(eq(roomWithOneUser.getChatRoomId()))).thenReturn(Optional.of(roomWithOneUser));
        when(chatRoomRepositoryMock.save(roomWithOneUser))
                .thenAnswer((Answer<ChatRoom>) invocation -> {
                    ChatRoom room = invocation.getArgument(0);
                    room.getConnectedUsers().add(0, joiningUser); // now users size = 1
                    room.getConnectedUsers().remove(1); // hardcoded to remove this user
                    return room;
                });

        chatRoomService.join(dto, chatRoomDto.getChatRoomId());

        assertThat(roomWithOneUser.getConnectedUsers().size(), is(1));
        verify(chatRoomRepositoryMock, times(1)).save(roomWithOneUser);
        verify(simpMessagingTemplateMock, times(1)).convertAndSend(
                destinationCaptor.capture(),
                messageObjectCaptor.capture());
        assertThat(roomWithOneUser.getConnectedUsers().contains(joiningUser), is(true));
    }

    @Test
    void whenUserLeaveChatRoomThenOperationIsSuccessful() {

        User userInRoom = new User();
        userInRoom.setUserId(1L);
        userInRoom.setUsername("John");
        userInRoom.setEmail("John@mail.ru");
        userInRoom.setPassword("0oklp9ij");

        ChatRoomDto dto = new ChatRoomDto();
        dto.setChatRoomId("2wesdss");
        dto.setName("leaving room");
        dto.setConnectedUsers(Collections.singletonList(userInRoom));

        assertThat(dto.getConnectedUsers().size(), is(1));

        UserDto leavingUser = new UserDto(1L, userInRoom.getUsername(), userInRoom.getEmail(), userInRoom.getPassword());

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setChatRoomId(dto.getChatRoomId());
        chatRoom.setName(dto.getName());
        chatRoom.setConnectedUsers(dto.getConnectedUsers());

        ChatRoom forReturn = new ChatRoom();
        forReturn.setChatRoomId(dto.getChatRoomId());
        forReturn.setName(dto.getName());

        when(chatRoomRepositoryMock.findById(eq(dto.getChatRoomId()))).thenReturn(Optional.of(forReturn));
        when(chatRoomRepositoryMock.save(any(ChatRoom.class))).thenReturn(forReturn);

        chatRoomService.leave(leavingUser, dto.getChatRoomId());

        assertThat(forReturn.getConnectedUsers().size(), is(0));
        verify(chatRoomRepositoryMock, times(1)).save(forReturn);
        verify(simpMessagingTemplateMock, times(1)).convertAndSend(
                destinationCaptor.capture(),
                messageCaptor.capture()
        );
        assertThat(forReturn.getConnectedUsers().contains(leavingUser), is(false));
    }

    @Test
    void whenUsersSendMessagesWithinChatRoomThanOperationIsSuccessful() throws Throwable {
        User client = new User("client", "client@gmail.com", "1qazxsw2");
        client.setUserId(1L);
        User manager = new User("manager", "manager@gmail.com", "0oplkmn");
        manager.setUserId(2L);

        ChatRoom chatRoom = new ChatRoom("live conversation", Arrays.asList(client, manager));
        chatRoom.setChatRoomId("id_1");
        assertThat(chatRoom.getConnectedUsers().size(), is(2));

        Message message = new Message();
        message.setChatRoomId(chatRoom.getChatRoomId());
        message.setFromUser(client.getUsername());
        message.setToUser(manager.getUsername());
        message.setContent("Hi!I have some problems with Builder pattern... Can you help me to learn it?");
        message.setDate(new Date());

        chatRoomService.sendMessage(message);

        verify(simpMessagingTemplateMock, times(2)).convertAndSendToUser(
                toUserCaptor.capture(),
                destinationCaptor.capture(),
                messageObjectCaptor.capture());
        verify(messageServiceMock, times(1)).sendMessageToConversation(message);

        List<String> toUsers = toUserCaptor.getAllValues();
        List<String> destinations = destinationCaptor.getAllValues();
        List<Object> messageObjects = messageObjectCaptor.getAllValues();

        String messageSentToManager = toUsers.get(0);
        String messageSentToClient = toUsers.get(1);

        String clientDestination = destinations.get(0);
        String managerDestination = destinations.get(1);

        Message toManagerMessageObject = (Message) messageObjects.get(0);
        Message toClientMessageObject = (Message) messageObjects.get(1);

        assertThat(clientDestination, is(DestinationUtil.chatRoomMessages(chatRoom.getChatRoomId())));
        assertEquals(message, toManagerMessageObject);
        assertThat(messageSentToManager, is(manager.getUsername()));

        assertThat(managerDestination, is(DestinationUtil.chatRoomMessages(chatRoom.getChatRoomId())));
        assertEquals(message, toClientMessageObject);
        assertThat(messageSentToClient, is(client.getUsername()));
    }
}
