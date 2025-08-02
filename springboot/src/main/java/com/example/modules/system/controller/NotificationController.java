package com.example.modules.system.controller;


import com.example.common.annotation.AuditLogRecord;
import com.example.common.result.R;

import com.example.core.controller.BaseController;
import com.example.core.service.BaseService;
import com.example.modules.system.entity.Notification;
import com.example.modules.system.service.NotificationService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController extends BaseController<Notification, Long> {
    private static final Log log = LogFactory.getLog(NotificationController.class);

    @Resource
    private NotificationService notificationService;

    protected NotificationController(BaseService<Notification, Long> baseService) {
        super(baseService);
    }

    @ApiOperation("发送通知")
    @AuditLogRecord(action = "发送通知", resource = "通知")
    @PostMapping("/send")
    public R<String>  sendNotification(@RequestBody Notification notification) {
        log.info("发送通知");
        notificationService.sendNotification(notification);
        return R.success("sent");
    }

    @ApiOperation("获取用户通知")
    @AuditLogRecord(action = "获取用户通知", resource = "通知")
    @GetMapping("/user/{userId}")
    public R<List<Notification>> getUserNotifications(@PathVariable Long userId) {
        log.info("获取用户通知");
        return R.success(notificationService.getUserNotifications(userId));
    }

    @ApiOperation("标记通知为已读")
    @AuditLogRecord(action = "标记通知为已读", resource = "通知")
    @PostMapping("/{id}/read")
    public R<String> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return R.success("marked as read");
    }

    @ApiOperation("删除通知")
    @AuditLogRecord(action = "删除通知", resource = "通知")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return R.ok();
    }
}
