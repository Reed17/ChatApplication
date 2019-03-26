package com.impltech.chatApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {
    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        /*messages
                //.nullDestMatcher().authenticated()
                .simpDestMatchers("/app/**", "/ws/**").hasAnyAuthority("USER")
                //.simpDestMatchers("/user/**").authenticated()
                .simpSubscribeDestMatchers("/topic/**", "/queue/**").hasAnyAuthority("USER")
                .anyMessage().denyAll();*/
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
