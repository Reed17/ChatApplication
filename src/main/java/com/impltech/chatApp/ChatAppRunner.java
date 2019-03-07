package com.impltech.chatApp;

import com.impltech.chatApp.config.WebSocketProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {WebSocketProperties.class})
public class ChatAppRunner {
    public static void main(String[] args) {
        SpringApplication.run(ChatAppRunner.class, args);
    }
}
