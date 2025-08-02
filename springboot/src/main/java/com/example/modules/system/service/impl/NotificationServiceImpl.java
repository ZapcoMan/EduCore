package com.example.modules.system.service.impl;

import java.util.List;

import com.example.core.service.impl.BaseServiceImpl;
import com.example.modules.system.entity.Notification;
import com.example.modules.system.mapper.NotificationMapper;
import com.example.modules.system.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通知服务实现类
 * 实现了NotificationService接口，用于处理通知相关的业务逻辑
 */
@Service
public class NotificationServiceImpl extends BaseServiceImpl<Notification, Long, NotificationMapper> implements NotificationService {

    /**
     * 注入通知数据访问对象
     */
    @Autowired
    private NotificationMapper notificationMapper;

    public NotificationServiceImpl(NotificationMapper mapper) {
        super(mapper);
    }

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

    /**
     * 根据ID查询通知信息
     *
     * @param id 通知ID
     * @return Notification 通知信息
     */
    @Override
    public Notification selectById(Long id) {
        return notificationMapper.selectById(id);
    }

    /**
     * 查询所有通知信息
     *
     * @return List<Notification> 通知信息列表
     */
    @Override
    public List<Notification> selectAll() {
        return List.of();
    }

    /**
     * 根据ID更新通知信息
     *
     * @param id 通知ID
     */
    @Override
    public void updateById(Long id) {
        notificationMapper.updateById(id);
    }

    /**
     * 插入通知信息
     *
     * @param notification 通知信息
     */
    @Override
    public void insert(Notification notification) {
        notificationMapper.insert(notification);
    }

    /**
     * 根据ID更新通知信息
     *
     * @param notification 通知信息
     */
    @Override
    public void updateById(Notification notification) {
        notificationMapper.updateById(notification);
    }

    /**
     * 根据ID删除通知信息
     *
     * @param id 通知ID
     */
    @Override
    public void deleteById(Long id) {
        notificationMapper.deleteById(id);
    }
}
