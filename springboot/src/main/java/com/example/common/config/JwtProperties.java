package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String secret;
    private long accessTokenExpire;   // 毫秒
    private long refreshTokenExpire;  // 毫秒

    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }

    public long getAccessTokenExpire() { return accessTokenExpire; }
    public void setAccessTokenExpire(long accessTokenExpire) { this.accessTokenExpire = accessTokenExpire; }

    public long getRefreshTokenExpire() { return refreshTokenExpire; }
    public void setRefreshTokenExpire(long refreshTokenExpire) { this.refreshTokenExpire = refreshTokenExpire; }
}