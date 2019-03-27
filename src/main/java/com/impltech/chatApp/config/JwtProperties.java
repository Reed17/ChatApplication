package com.impltech.chatApp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secretKey;
    private Long expirationTimeMs;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Long getExpirationTimeMs() {
        return expirationTimeMs;
    }

    public void setExpirationTimeMs(Long expirationTimeMs) {
        this.expirationTimeMs = expirationTimeMs;
    }
}