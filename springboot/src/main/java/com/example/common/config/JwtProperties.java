package com.example.common.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置属性类
 * 用于读取和存储JWT相关的配置信息，包括密钥和令牌过期时间
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    /**
     * JWT签名密钥
     */
    private String secret;

    /**
     * 访问令牌过期时间（毫秒）
     */
    private long accessTokenExpire;   // 毫秒

    /**
     * 刷新令牌过期时间（毫秒）
     */
    private long refreshTokenExpire;  // 毫秒

}
