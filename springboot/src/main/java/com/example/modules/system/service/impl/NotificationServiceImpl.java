package com.example.modules.system.service.impl;

import java.util.List;

import com.example.modules.system.entity.Notification;
import com.example.modules.system.mapper.NotificationMapper;
import com.example.modules.system.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通知服务实现类
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    /**
     * 注入通知数据访问对象
     */
    @Autowired
    private NotificationMapper notificationMapper;

    /**
     * 发送通知
     *
     * @param notification 待发送的通知对象
     */
    @Override
    public void sendNotification(Notification notification) {
        notificationMapper.insert(notification);
    }

    /**
     * 获取用户的通知列表
     *
     * @param userId 用户ID
     * @return 用户的通知列表
     */
    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return notificationMapper.findByUser(userId);
    }

    /**
     * 将通知标记为已读
     *
     * @param id 通知的ID
     */
    @Override
    public void markAsRead(Long id) {
        notificationMapper.markAsRead(id);
    }

    /**
     * 删除通知
     *
     * @param id 通知的ID
     */
    @Override
    public void deleteNotification(Long id) {
        notificationMapper.deleteLogical(id);
    }
}
