package com.example.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限验证注解，用于标记需要特定权限才能访问的方法
 * 该注解只能用于方法上，在运行时保留，配合AOP或拦截器实现权限验证功能
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermission {
    /**
     * 权限标识值
     * @return 权限字符串标识
     */
    String value();
}
