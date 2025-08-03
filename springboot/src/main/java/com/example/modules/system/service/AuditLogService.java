package com.example.modules.system.service;

import com.example.core.service.BaseService;
import com.example.modules.system.entity.AuditLog;

import java.util.List;

/**
 * 审计日志服务接口，定义了审计日志的操作
 */
public interface AuditLogService extends BaseService<AuditLog, Long> {

    /**
     * 保存审计日志
     *
     * @param log 审计日志对象，包含日志的详细信息
     */
    void saveLog(AuditLog log);

    /**
     * 获取最近的审计日志
     *
     * @param limit 限制获取的日志数量，用于控制返回结果的大小
     * @return 最近的审计日志列表，按时间降序排列
     */
    List<AuditLog> getRecentLogs(int limit);
}

