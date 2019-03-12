package com.impltech.chatApp;

import com.impltech.chatApp.config.ChatRelayProperties;
import com.impltech.chatApp.config.WebSocketProperties;
import com.impltech.chatApp.repository.MessageRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {MessageRepository.class})
@EnableConfigurationProperties(value = {
        WebSocketProperties.class,
        ChatRelayProperties.class
})
public class ChatAppRunner {
    public static void main(String[] args) {
        SpringApplication.run(ChatAppRunner.class, args);
    }
}
