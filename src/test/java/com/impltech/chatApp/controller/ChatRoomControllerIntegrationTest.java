package com.impltech.chatApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.impltech.chatApp.dto.ChatRoomDto;
import com.impltech.chatApp.dto.UserDto;
import com.impltech.chatApp.entity.User;
import com.impltech.chatApp.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
public class ChatRoomControllerIntegrationTest {

    public static final String API_CHATROOM_LEAVE = "/chatroom/{id}/leave";
    private static final String API_CHATROOM = "/chatroom/new";
    private static final String API_CHATS = "/chats";
    public static final String API_CHATROOM_JOIN = "/chatroom/{id}/join";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    void whenAuthorizedUserCreatesNewChatRoomThenReturnStatusIsCreated() throws Exception {
        ChatRoomDto dto = new ChatRoomDto("new room", Collections.emptyList());

        this.mockMvc.perform(post(API_CHATROOM)
                .with(user("mocked user")
                        .authorities(new SimpleGrantedAuthority("USER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenAuthorizedUserGetAllChatRoomsThenReturnStatusIsOk() throws Exception {
        this.mockMvc.perform(get(API_CHATS)
                .with(user("John")
                        .authorities(new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isOk());
    }

    @Test
    void whenAuthorizedUserJoinChatRoomThenReturnStatusIsOk() throws Exception {
        ChatRoomDto emptyRoom = getChatRoomDto();

        String response = createChatRoom(emptyRoom);

        ChatRoomDto createdRoom = this.objectMapper.readValue(response, ChatRoomDto.class);

        UserDto newUser = getUserDto();

        // todo imitate client join
        this.mockMvc.perform(put(API_CHATROOM_JOIN, createdRoom.getChatRoomId())
                .with(user("Mikhail")
                        .authorities(new SimpleGrantedAuthority("USER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk());
    }

    @Test
    void whenAuthorizedUserLeaveTheRoomThenReturnStatusIsOk() throws Exception {
        String createdResponse = createChatRoom(new ChatRoomDto("full room", Arrays.asList(
                new User("client", "cli@gmail.com", "1qw2sazx"),
                new User("manager", "mngr@gmail.com", "2wsxcde3"))));

        ChatRoomDto roomWithUsers = this.objectMapper.readValue(createdResponse, ChatRoomDto.class);

        // todo imitate client leave
        this.mockMvc.perform(put(API_CHATROOM_LEAVE, roomWithUsers.getChatRoomId())
                .with(user("client")
                        .authorities(new SimpleGrantedAuthority("USER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(roomWithUsers.getConnectedUsers().get(0))))
                .andExpect(status().isOk());
    }

    @Test
    void whenUnauthorizedUserTriesToCreateTheRoomThenReturnStatusIsUnauthorized() throws Exception {
        ChatRoomDto emptyRoom = new ChatRoomDto("empty room", Collections.emptyList());

        this.mockMvc.perform(post(API_CHATROOM)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(emptyRoom)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenUnauthorizedUserTriesToGetAllChatRoomsThenReturnStatusIsUnauthorized() throws Exception {
        this.mockMvc.perform(get(API_CHATS))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenUnauthorizedUserTriesToJoinTheChatRoomThenReturnStatusIsUnauthorized() throws Exception {
        String answer = createChatRoom(new ChatRoomDto("empty room", Collections.emptyList()));

        ChatRoomDto emptyRoom = this.objectMapper.readValue(answer, ChatRoomDto.class);
        UserDto userDto = getUserDto();

        this.mockMvc.perform(put(API_CHATROOM_JOIN, emptyRoom.getChatRoomId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isUnauthorized());
    }

    private ChatRoomDto getChatRoomDto() {
        return new ChatRoomDto("chat room for test", Collections.emptyList());
    }

    private UserDto getUserDto() {
        return new UserDto(
                1L,
                "Mikhail",
                "misha@mail.ru",
                "3edcxsw2",
                Collections.singleton(Role.USER));
    }

    private String createChatRoom(ChatRoomDto emptyRoom) throws Exception {
        return this.mockMvc.perform(post(API_CHATROOM)
                .with(user("test")
                        .authorities(new SimpleGrantedAuthority("USER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(emptyRoom)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

}
