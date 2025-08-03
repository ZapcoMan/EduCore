package com.example.log.aspect;

import com.example.common.annotation.AuditLogRecord;
import com.example.modules.system.entity.AuditLog;
import com.example.modules.system.service.AuditLogService;


import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

// 定义一个切面类，用于处理审计日志
@Aspect
@Component
public class AuditLogAspect {
    // 日志对象，用于记录审计日志方面的信息
    private static final Log log = LogFactory.getLog(AuditLogAspect.class);

    // 注入审计日志服务，用于保存审计日志
    @Resource
    private AuditLogService auditLogService;

    /**
     * 处理审计日志记录的环绕通知方法
     * @param joinPoint 切点信息，包含被拦截的方法执行信息
     * @param auditLogRecord 注解对象，包含审计日志的元数据
     * @return Object 被拦截方法的执行结果
     * @throws Throwable 方法执行过程中可能抛出的异常
     */
    @Around("@annotation(auditLogRecord)")
    public Object handleAudit(ProceedingJoinPoint joinPoint, AuditLogRecord auditLogRecord) throws Throwable {
        Object result = null;
        // 获取当前登录用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // 获取客户端IP地址
        String ip = RequestContextHolder.currentRequestAttributes() instanceof ServletRequestAttributes
                ? ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr()
                : "unknown";

        try {
            // 执行被拦截的方法
            result = joinPoint.proceed();
            return result;
        } finally {
            /*
             * 创建并保存审计日志记录
             * 记录用户名、操作类型、资源类型、IP地址和操作详情
             */
            // 创建审计日志对象
            AuditLog log = AuditLog.builder()
                    .username(username)
                    .action(auditLogRecord.action())
                    .resource(auditLogRecord.resource())
                    .ipAddress(ip)
                    .details(Arrays.toString(joinPoint.getArgs()))
                    .build();
            // 保存审计日志
            auditLogService.saveLog(log);
        }
    }

}
