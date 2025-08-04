package com.example.common.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * 配置MyBatis相关设置，包括Mapper接口的扫描路径
 */
@Configuration
@MapperScan("com.example.modules.system.mapper")
public class MyBatisConfig {
}
