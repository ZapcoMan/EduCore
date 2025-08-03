package com.example.modules.system.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 审计日志类，用于记录系统中的操作日志
 * 这些日志包括用户执行的操作、操作的时间、以及操作的资源等信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
    /**
     * 日志记录的唯一标识符
     */
    private Long id;

    /**
     * 执行操作的用户名
     */
    private String username;

    /**
     * 操作类型，描述了用户具体执行的动作
     */
    private String action;

    /**
     * 受操作影响的资源，可以是数据库表、页面等
     */
    private String resource;

    /**
     * 用户的IP地址，用于追踪操作来源
     */
    private String ipAddress;

    /**
     * 操作的详细信息，可能包括操作前后的数据变化等
     */
    private String details;

    /**
     * 操作发生的时间，使用时间戳格式记录
     */
    private Timestamp createdAt;
}
