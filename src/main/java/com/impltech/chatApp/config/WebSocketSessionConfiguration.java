package com.impltech.chatApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.Session;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketSessionConfiguration extends AbstractSessionWebSocketMessageBrokerConfigurer<Session> {

    private final WebSocketProperties webSocketProperties;
    private final ChatRelayProperties chatRelayProperties;

    @Autowired
    public WebSocketSessionConfiguration(final WebSocketProperties webSocketProperties,
                                         final ChatRelayProperties chatRelayProperties) {
        this.webSocketProperties = webSocketProperties;
        this.chatRelayProperties = chatRelayProperties;
    }

    @Override
    protected void configureStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint(
                        webSocketProperties.getEndpoint())
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry
                .enableStompBrokerRelay(
                        webSocketProperties.getTopic(),
                        webSocketProperties.getQueue())
                .setUserDestinationBroadcast("/topic/unresolved.user.dest")
                .setUserRegistryBroadcast("/topic/registry.broadcast")
                .setRelayHost(chatRelayProperties.getHost())
                .setRelayPort(chatRelayProperties.getPort());

        registry.setApplicationDestinationPrefixes(webSocketProperties.getAppPrefix());
    }
}
