package com.example.common.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.modules.system.mapper")
public class MyBatisConfig {
}

