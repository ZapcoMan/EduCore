package com.example.modules.system.controller;


import com.example.common.annotation.AuditLogRecord;
import com.example.common.result.R;
import com.example.modules.system.entity.Teacher;
import com.example.modules.system.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教师控制器类，处理与教师相关的HTTP请求
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private static final Log log = LogFactory.getLog(TeacherController.class);
    /**
     * 注入教师服务类，用于处理教师相关的业务逻辑
     */
    @Resource
    private TeacherService teacherService;

    /**
     * 获取教师列表
     *
     * @return 包含教师列表的响应对象
     */
    @ApiOperation("获取教师列表")
    @AuditLogRecord(action = "Get TeacherList", resource = "Teacher")
    @GetMapping
    public R<List<Teacher>> list() {
        return R.success(teacherService.list());
    }

    /**
     * 根据ID获取教师信息
     *
     * @param id 教师的ID
     * @return 包含教师信息的响应对象
     */
    @ApiOperation("根据ID获取教师信息")
    @AuditLogRecord(action = "Get Teacher", resource = "Teacher")
    @GetMapping("/{id}")
    public R<Teacher> get(@PathVariable Long id) {
        log.info("Teacher id = " + id);
        return R.success(teacherService.getById(id));
    }

    /**
     * 创建教师
     *
     * @param teacher 教师对象，包含要创建教师的信息
     * @return 表示创建结果的响应对象
     */
    @ApiOperation("创建教师")
    @AuditLogRecord(action = "Create Teacher", resource = "Teacher")
    @PostMapping
    public R<Object> create(@RequestBody Teacher teacher) {
        log.info("Create Teacher  = " + teacher);
        teacherService.create(teacher);
        return R.ok().message("教师创建成功");
    }

    /**
     * 更新教师信息
     *
     * @param teacher 教师对象，包含要更新的教师信息
     * @return 表示更新结果的响应对象
     */
    @ApiOperation("更新教师信息")
    @AuditLogRecord(action = "Update Teacher", resource = "Teacher")
    @PutMapping
    public R<Object> update(@RequestBody Teacher teacher) {
        log.info("Update Teacher  = " + teacher);
        teacherService.update(teacher);
        return R.ok().message("教师更新完成");
    }

    /**
     * 根据ID删除教师
     *
     * @param id 要删除的教师的ID
     */
    @ApiOperation("根据ID删除教师")
    @AuditLogRecord(action = "Delete Teacher", resource = "Teacher")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Delete Teacher By ID = " + id);
        teacherService.delete(id);
    }

    /**
     * 根据UserID获取教师信息
     *
     * @param userId 用户ID，用于查找教师
     * @return 对应UserID的教师对象
     */
    @ApiOperation("根据UserID获取教师信息")
    @AuditLogRecord(action = "Get Teacher by UserId", resource = "Teacher")
    @GetMapping("/user/{userId}")
    public R<Teacher> getByUserId(@PathVariable Long userId) {
        log.info("Get Teacher by UserId = " + userId);
        return R.success(teacherService.getByUserId(userId));
    }

}
