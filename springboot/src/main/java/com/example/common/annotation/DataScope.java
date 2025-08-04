package com.example.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限注解，用于控制用户数据访问范围
 * 该注解只能用于方法上，在运行时保留，配合AOP实现数据权限过滤功能
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {
    /**
     * 表别名，用于 SQL 拼接，如 "u"
     */
    String userAlias() default "";

    /**
     * 部门表别名，用于 SQL 拼接
     * @return 部门表别名
     */
    String deptAlias() default "";
}
