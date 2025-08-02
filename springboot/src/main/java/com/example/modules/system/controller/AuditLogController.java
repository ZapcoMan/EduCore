package com.example.modules.system.controller;


import com.example.common.annotation.AuditLogRecord;
import com.example.common.result.R;
import com.example.core.controller.BaseController;
import com.example.core.service.BaseService;
import com.example.modules.system.entity.AuditLog;
import com.example.modules.system.service.AuditLogService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 审计日志控制器类，提供审计日志相关的API接口
 * 该控制器用于处理系统审计日志的查询和管理操作
 */
@RestController
@RequestMapping("/api/audit")
public class AuditLogController extends BaseController<AuditLog, Long> {

    /**
     * 注入审计日志服务，用于处理审计日志的业务逻辑
     */
    @Resource
    private AuditLogService auditLogService;

    /**
     * 日志记录器，用于记录控制器类的日志信息
     */
    private static final Log log = LogFactory.getLog(AuditLogController.class);

    protected AuditLogController(BaseService<AuditLog, Long> baseService) {
        super(baseService);
    }

    /**
     * 获取最近的审计日志
     * 该方法用于查询系统中最近产生的审计日志记录，按照时间倒序排列
     *
     * @param limit 限制获取的日志数量，默认为20条
     * @return 返回最近的审计日志列表，封装在R对象中
     */
    @ApiOperation("获取最近的审计日志")
    @AuditLogRecord(action = "获取最近的审计日志" ,resource = "审计日志")
    @GetMapping("/recent")
    public R<List<AuditLog>> getRecentLogs(@RequestParam(defaultValue = "20") int limit) {
        log.info("Getting recent logs with limit: " + limit);
        return R.success(auditLogService.getRecentLogs(limit));
    }
}
