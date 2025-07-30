package com.example.common.config;

import com.example.security.JwtAuthenticationFilter;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 配置安全过滤链
     * @param http HTTP安全对象
     * @return 构建好的安全过滤链
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 配置CORS和CSRF策略
        http
                .cors()
                .and()
                .csrf().disable()
                // 配置请求授权规则
                .authorizeHttpRequests()
                .requestMatchers(
                        "/login", "/register",
                        "/files/upload/**", "/files/download/**",
                        "/favicon.ico","qrcode/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().disable();

        // 添加JWT认证过滤器
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 配置全局的跨域资源共享(CORS)策略
     * @return 配置好的UrlBasedCorsConfigurationSource对象
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // 创建CORS配置对象
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 允许所有来源访问，部署时建议改为实际域名
        config.addAllowedOriginPattern("*");  
        // 允许所有头部信息
        config.setAllowedHeaders(List.of("*"));
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // 允许所有请求方法
        config.setAllowedMethods(List.of("*"));

        // 创建URL基础的CORS配置源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 注册CORS配置，对所有URL路径生效
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

