package com.impltech.chatApp.service;

import com.impltech.chatApp.dto.ChatRoomDto;
import com.impltech.chatApp.dto.UserDto;
import com.impltech.chatApp.entity.ChatRoom;
import com.impltech.chatApp.entity.Message;
import com.impltech.chatApp.mapper.ChatRoomMapper;
import com.impltech.chatApp.mapper.UserMapper;
import com.impltech.chatApp.repository.ChatRoomRepository;
import com.impltech.chatApp.repository.MessageRepository;
import com.impltech.chatApp.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
public class MongoMessageServiceUnitTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatRoomMapper chatRoomMapper;

    @Autowired
    private UserMapper userMapper;

    private ChatRoom chatRoom;

    @BeforeEach
    void setup() {
        this.chatRoom = new ChatRoom("conversation room", Collections.emptyList());
        this.chatRoomRepository.save(chatRoom);
    }

    @AfterEach
    void tearDown() {
        this.chatRoomRepository.deleteAll();
        this.messageRepository.deleteAll();
    }

    @Test
    void whenUsersMessagingThenOperationSuccessful() {
        UserDto client = new UserDto("super client", "cl1ent@mail.ru", "0192irfh");
        UserDto manager = new UserDto("supreme manager", "pm@bigmir.net", "ppoe90io");

        Optional<ChatRoom> chatRoomWrapper = chatRoomRepository.findById(this.chatRoom.getChatRoomId());
        ChatRoom actualRoom = chatRoomWrapper.get();
        ChatRoomDto emptyRoom = chatRoomMapper.toDto(actualRoom);

        ChatRoomDto clientJoined = joinToRoom(client, emptyRoom.getChatRoomId());
        ChatRoomDto managerJoined = joinToRoom(manager, clientJoined.getChatRoomId());

        assertThat(managerJoined.getConnectedUsers().size(), is(2));

        Message questionToManager = getQuestionToManager(client, manager);
        questionToManager.setChatRoomId(managerJoined.getChatRoomId());
        chatRoomService.sendMessage(questionToManager);

        List<Message> managerMessages = getMessageHistoryFor(manager, managerJoined);

        assertThat(managerMessages.size(), is(1));
        assertEquals(manager.getUsername(), managerMessages.get(0).getUsername());

        Message replyToClient = getReplyToClient(client, manager);
        replyToClient.setChatRoomId(managerJoined.getChatRoomId());
        chatRoomService.sendMessage(replyToClient);

        List<Message> clientMessages = getMessageHistoryFor(client, managerJoined);

        assertThat(clientMessages.size(), is(1));
        assertEquals(client.getUsername(), clientMessages.get(0).getUsername());
    }

    private ChatRoomDto joinToRoom(UserDto client, String chatRoomId) {
        return chatRoomService.join(client, chatRoomId);
    }

    private List<Message> getMessageHistoryFor(UserDto manager, ChatRoomDto managerJoined) {
        return messageService.findMessageHistoryFor(manager.getUsername(), managerJoined.getChatRoomId());
    }

    private Message getQuestionToManager(UserDto client, UserDto manager) {
        return new Message(
                new Date(),
                client.getUsername(),
                manager.getUsername(),
                "Hi! My name is super client! I have few question about the pharmacy.");
    }

    private Message getReplyToClient(UserDto client, UserDto manager) {
        return new Message(
                manager.getUsername(),
                new Date(),
                manager.getUsername(),
                client.getUsername(),
                "Hi super client! Let`s start!");
    }
}
