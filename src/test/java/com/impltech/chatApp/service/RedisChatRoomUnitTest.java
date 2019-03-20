package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.ChatRoomDto;
import com.impltech.chatApp.dto.UserDto;
import com.impltech.chatApp.entity.ChatRoom;
import com.impltech.chatApp.entity.User;
import com.impltech.chatApp.mapper.ChatRoomMapper;
import com.impltech.chatApp.repository.ChatRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class RedisChatRoomUnitTest {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatRoomMapper chatRoomMapper;

    private ChatRoom chatRoom;

    @BeforeEach
    void setup() {
        this.chatRoom = new ChatRoom("conversation room", Collections.emptyList());
        chatRoomRepository.save(chatRoom);
    }

    @BeforeEach
    void tearDown() {
        chatRoomRepository.deleteAll();
    }

    @Test
    void whenUserTryToJoinTheRoomThenOperationSuccessful() {
        Optional<ChatRoom> chatRoomWrapper = chatRoomRepository.findById(this.chatRoom.getChatRoomId());
        ChatRoom emptyChatRoom = chatRoomWrapper.get();

        assertThat(emptyChatRoom.getConnectedUsers().size(), is(0));

        ChatRoomDto chatRoomDto = chatRoomMapper.toDto(emptyChatRoom);

        UserDto client = new UserDto("client_1", "cli@gmail.com", "0polkmnu");
        UserDto manager = new UserDto("manager_1", "mngr@gmail.com", "56tyghvn");

        // todo emulate the client connection
        ChatRoomDto roomDtoWithConnectedClient = chatRoomService.join(client, chatRoomDto);

        assertThat(roomDtoWithConnectedClient.getConnectedUsers().size(), is(1));

        // todo emulate manager connection
        ChatRoomDto roomWithClientAndManager = chatRoomService.join(manager, roomDtoWithConnectedClient);

        assertThat(roomWithClientAndManager.getConnectedUsers().size(), is(2));

        List<User> connectedUsers = roomWithClientAndManager.getConnectedUsers();

        assertEquals(client.getUsername(), connectedUsers.get(0).getUsername());
        assertEquals(manager.getUsername(), connectedUsers.get(1).getUsername());
    }

    @Test
    void whenUserTryToLeaveTheRoomThenOperationSuccessful() {
        Optional<ChatRoom> chatRoomWrapper = chatRoomRepository.findById(this.chatRoom.getChatRoomId());
        ChatRoom emptyChatRoom = chatRoomWrapper.get();

        // todo ensure the room is empty
        assertThat(emptyChatRoom.getConnectedUsers().size(), is(0));

        UserDto client = new UserDto("new client", "newCli@gmail.com", "7778ujhb");
        UserDto manager = new UserDto("free manager", "freeMngr@gmail.com", "34gt56sa");
        ChatRoomDto emptyChatRoomDto = chatRoomMapper.toDto(emptyChatRoom);

        // todo emulate client and manager connections
        chatRoomService.join(client, emptyChatRoomDto);
        ChatRoomDto roomWithClientAndManager = chatRoomService.join(manager, emptyChatRoomDto);

        assertThat(roomWithClientAndManager.getConnectedUsers().size(), is(2));

        // todo emulate client left the room
        ChatRoomDto roomWithManager = chatRoomService.leave(client, roomWithClientAndManager);

        // todo ensure client left
        List<User> userList = roomWithManager.getConnectedUsers();

        assertThat(userList.size(), is(1));
        assertEquals(manager.getUsername(), userList.get(0).getUsername());

        // todo emulate manager left the room
        ChatRoomDto emptyRoom = chatRoomService.leave(manager, roomWithManager);

        // todo ensure manager left the room
        assertThat(emptyRoom.getConnectedUsers().size(), is(0));
    }

}
