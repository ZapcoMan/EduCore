package com.example.modules.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * 通知类
 * 用于表示系统中的通知信息，包括通知的ID、用户ID、类型、标题、内容、状态以及创建和阅读时间
 */
public class Notification {
    /**
     * 通知的唯一标识符
     */
    private Long id;

    /**
     * 接收通知的用户的ID
     */
    private Long userId;

    /**
     * 通知的类型，例如“系统通知”、“消息提醒”等
     */
    private String type;

    /**
     * 通知的标题
     */
    private String title;

    /**
     * 通知的内容
     */
    private String content;

    /**
     * 通知的状态，例如“未读”、“已读”
     */
    private String status;

    /**
     * 通知的创建时间
     */
    private Timestamp createdAt;

    /**
     * 通知的阅读时间
     */
    private Timestamp readAt;
}
