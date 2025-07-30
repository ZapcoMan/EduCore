package com.example.modules.system.controller;


import com.example.common.annotation.AuditLogRecord;
import com.example.common.result.R;
import com.example.modules.system.entity.Course;
import com.example.modules.system.service.CourseService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseService courseService;


    private static final Log log = LogFactory.getLog(CourseController.class);

    @ApiOperation("获取课程列表")
    @AuditLogRecord(action = "Get CourseList", resource = "Course")
    @GetMapping
    public R list() {
        log.info("Get CourseList");
        return R.success(courseService.list());
    }

    @ApiOperation("根据ID获取课程信息")
    @AuditLogRecord(action = "Get Course", resource = "Course")
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        log.info("Get Course id = " + id);
        return R.success(courseService.getById(id));
    }

    @ApiOperation("创建课程")
    @AuditLogRecord(action = "Create Course", resource = "Course")
    @PostMapping
    public void create(@RequestBody Course course) {
        log.info("Create Course = " + course);
        courseService.create(course);
    }

    @ApiOperation("更新课程信息")
    @AuditLogRecord(action = "Update Course", resource = "Course")
    @PutMapping
    public void update(@RequestBody Course course) {
        log.info("Update Course = " + course);
        courseService.update(course);
    }

    @ApiOperation("根据ID删除课程")
    @AuditLogRecord(action = "Delete Course", resource = "Course")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info("Delete Course By ID = " + id);
        courseService.delete(id);
    }
}
