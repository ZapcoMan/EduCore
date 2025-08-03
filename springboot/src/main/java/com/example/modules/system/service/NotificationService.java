package com.example.modules.system.service;



import com.example.core.service.BaseService;
import com.example.modules.system.entity.Notification;

import java.util.List;

/**
 * 通知服务接口，提供通知相关的操作
 */
public interface NotificationService extends BaseService<Notification, Long> {

    /**
     * 发送通知
     *
     * @param notification 要发送的通知对象
     */
    void sendNotification(Notification notification);

    /**
     * 获取用户的通知列表
     *
     * @param userId 用户ID
     * @return 用户的通知列表
     */
    List<Notification> getUserNotifications(Long userId);

    /**
     * 将通知标记为已读
     *
     * @param id 要标记为已读的通知的ID
     */
    void markAsRead(Long id);

    /**
     * 删除通知
     *
     * @param id 要删除的通知的ID
     */
    void deleteNotification(Long id);
}

