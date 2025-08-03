package com.example.common.advice;

import com.example.common.result.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice(basePackages = "com.example") // ✅ 限定只拦截本项目
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    // ✅ 需要排除统一封装的路径（支持通配符）
    private static final List<String> EXCLUDE_PATH_PATTERNS = Arrays.asList(
            "/swagger/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/doc.html",
            "/favicon.ico",
            "/actuator/**",
            "/files/**"
    );

    @Override
    public boolean supports(@Nullable MethodParameter returnType,
                            @Nullable Class<? extends HttpMessageConverter<?>> converterType) {
        return true; // 统一处理所有 Controller 的响应（但下方具体做排除）
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @Nullable MethodParameter returnType,
                                  @Nullable MediaType selectedContentType,
                                  @Nullable Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @Nullable ServerHttpRequest request,
                                  @Nullable ServerHttpResponse response) {

        String path = null;
        if (request != null) {
            path = request.getURI().getPath();
        }

        // ✅ 判断是否排除当前请求路径
        if (shouldSkipPath(path)) {
            return body;
        }

        // ✅ 已是 R 类型，直接返回
        if (body instanceof R) {
            return body;
        }

        // ✅ 特殊处理 String 类型（避免类型转换异常）
        if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(R.ok(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("字符串类型响应封装失败", e);
            }
        }

        // ✅ null 也包装一层
        if (body == null) {
            return R.ok(null);
        }

        // ✅ 默认对象统一封装
        return R.ok(body);
    }

    // ✅ 判断路径是否命中排除列表
    private boolean shouldSkipPath(String path) {
        return EXCLUDE_PATH_PATTERNS.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
}
