package com.example.common.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于记录审计日志的注解。
 * 
 * @Retention 表示该注解在运行时可用
 * @Target 表示该注解可以应用在方法上
 * 
 * @action 审计日志的操作类型，例如 "create", "update", "delete" 等
 * @resource 审计日志的资源名称，默认为空字符串
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuditLogRecord {
    String action();
    String resource() default "";
}