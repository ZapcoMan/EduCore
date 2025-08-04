package com.example.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis配置类，用于配置Redis相关Bean
 * 提供RedisTemplate和StringRedisTemplate的Bean定义
 */
@Configuration
public class RedisConfig {

    /**
     * 配置RedisTemplate Bean，用于操作Redis中的对象数据
     * @param factory Redis连接工厂
     * @return RedisTemplate实例，key为String类型，value为Object类型
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 配置序列化器等（略）
        return template;
    }

    /**
     * 配置StringRedisTemplate Bean，用于操作Redis中的字符串数据
     * @param factory Redis连接工厂
     * @return StringRedisTemplate实例
     */
    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }
}
