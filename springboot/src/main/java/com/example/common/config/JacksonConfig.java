package com.example.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jackson配置类
 * 用于配置和提供Jackson ObjectMapper Bean，处理JSON序列化和反序列化
 */
@Configuration
public class JacksonConfig {
    /**
     * 创建并配置ObjectMapper Bean
     * ObjectMapper用于处理Java对象与JSON之间的序列化和反序列化
     * @return ObjectMapper实例
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
