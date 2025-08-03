package com.example.modules.system.mapper;

import com.example.core.mapper.BaseMapper;
import com.example.modules.system.entity.AuditLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 审计日志Mapper接口，用于执行与审计日志相关的数据库操作
 */
@Mapper
public interface AuditLogMapper extends BaseMapper<AuditLog, Long> {

    /**
     * 插入一条审计日志记录
     *
     * @param log 审计日志对象，包含要插入记录的信息
     */
    @Insert("INSERT INTO audit_log(username, action, resource, ip_address, details, created_at) " +
            "VALUES(#{username}, #{action}, #{resource}, #{ipAddress}, #{details}, NOW())")
    void insert(AuditLog log);

    /**
     * 选择最近的审计日志记录
     *
     * @param limit 限制返回的记录数量
     * @return 最近的审计日志记录列表
     */
    @Select("SELECT * FROM audit_log ORDER BY created_at DESC LIMIT #{limit}")
    List<AuditLog> selectRecent(@Param("limit") int limit);

    @Update("UPDATE audit_log SET username = #{username}, action = #{action}, resource = #{resource}, ip_address = #{ipAddress}, details = #{details}, created_at = NOW() WHERE id = #{id}")
    void updateById(Long id);
}

