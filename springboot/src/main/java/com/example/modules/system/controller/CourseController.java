package com.example.modules.system.controller;


import com.example.common.annotation.AuditLogRecord;
import com.example.common.result.R;
import com.example.core.controller.BaseController;
import com.example.core.service.BaseService;
import com.example.modules.system.entity.Course;
import com.example.modules.system.service.CourseService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 课程控制器类
 * 处理与课程相关的HTTP请求，包括增删改查等操作
 */
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController<Course, Long> {

    @Resource
    private CourseService courseService;

    private static final Log log = LogFactory.getLog(CourseController.class);

    protected CourseController(BaseService<Course, Long> baseService) {
        super(baseService);
    }

    /**
     * 创建课程
     *
     * @param course 课程对象，包含要创建的课程信息
     * @return R<Void> 表示创建结果的响应对象
     */
    @ApiOperation("创建课程")
    @AuditLogRecord(action = "Create Course", resource = "Course")
    @PostMapping
    public R<Void> create(@RequestBody Course course) {
        log.info("Create Course = " + course);
        courseService.create(course);
        return R.ok(); // 创建成功，无具体数据返回，直接返回成功响应
    }

    /**
     * 更新课程信息
     *
     * @param course 课程对象，包含要更新的课程信息
     * @return R<Void> 表示更新结果的响应对象
     */
    @ApiOperation("更新课程信息")
    @AuditLogRecord(action = "Update Course", resource = "Course")
    @PutMapping
    public R<Void> update(@RequestBody Course course) {
        log.info("Update Course = " + course);
        courseService.update(course);
        return R.ok(); // 更新成功
    }

    /**
     * 根据ID删除课程
     *
     * @param id 要删除的课程ID
     * @return R<Void> 表示删除结果的响应对象
     */
    @ApiOperation("根据ID删除课程")
    @AuditLogRecord(action = "Delete Course", resource = "Course")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        log.info("Delete Course By ID = " + id);
        courseService.delete(id);
        return R.ok(); // 删除成功
    }
}
