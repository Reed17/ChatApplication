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
    }

    @Test
    void whenUsersMessagingThenOperationSuccessful() {
        UserDto client = new UserDto("super client", "cl1ent@mail.ru", "0192irfh");
        UserDto manager = new UserDto("supreme manager", "pm@bigmir.net", "ppoe90io");

        Optional<ChatRoom> chatRoomWrapper = chatRoomRepository.findById(this.chatRoom.getChatRoomId());
        ChatRoom actualRoom = chatRoomWrapper.get();
        ChatRoomDto emptyRoom = chatRoomMapper.toDto(actualRoom);

        ChatRoomDto clientJoined = chatRoomService.join(client, emptyRoom);
        ChatRoomDto managerJoined = chatRoomService.join(manager, clientJoined);

        assertThat(managerJoined.getConnectedUsers().size(), is(2));

        Message questionToManager = new Message(
                new Date(),
                client.getUsername(),
                manager.getUsername(),
                "Hi! My name is super client! I have few question about the pharmacy.");
        //questionToManager.setUsername(client.getUsername());
        questionToManager.setChatRoomId(managerJoined.getChatRoomId());
        chatRoomService.sendMessage(questionToManager);

        List<Message> managerMessages = messageService.findMessageHistoryFor(manager.getUsername(), managerJoined.getChatRoomId());
        System.out.println(managerMessages);
        assertThat(managerMessages.size(), is(1));

        Message replyToClient = new Message(
                manager.getUsername(),
                new Date(),
                manager.getUsername(),
                client.getUsername(),
                "Hi super client! Let`s start!");
        replyToClient.setChatRoomId(managerJoined.getChatRoomId());
        chatRoomService.sendMessage(replyToClient);

        List<Message> clientMessages = messageService.findMessageHistoryFor(client.getUsername(), managerJoined.getChatRoomId());
        System.out.println(clientMessages);

        assertThat(clientMessages.size(), is(1));

        Message firstReplyToManager = new Message(new Date(), client.getUsername(), manager.getUsername(), "Very well! My question is, how much is that medicine cost?");
        chatRoomService.sendMessage(firstReplyToManager);

        List<Message> managerMsgHistoryShouldBeUpdated = messageService.findMessageHistoryFor(manager.getUsername(), managerJoined.getChatRoomId());
        System.out.println(managerMsgHistoryShouldBeUpdated);

        List<Message> all = messageRepository.findAll();
        System.out.println(all);

    }
}
