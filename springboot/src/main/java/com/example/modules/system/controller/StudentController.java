package com.example.modules.system.controller;


import com.example.common.annotation.AuditLogRecord;
import com.example.common.result.R;
import com.example.core.controller.BaseController;
import com.example.core.service.BaseService;
import com.example.modules.system.entity.Student;
import com.example.modules.system.service.StudentService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

/**
 * 学生控制器类，处理与学生相关的HTTP请求
 */
@RestController
@RequestMapping("/student")
public class StudentController  extends BaseController<Student, Long> {
    // 静态日志记录器
    private static final Log log = LogFactory.getLog(StudentController.class);

    // 学生服务接口的实例
    @Resource
    private StudentService studentService;

    protected StudentController(BaseService<Student, Long> baseService) {
        super(baseService);
    }



    /**
     * 创建新学生
     *
     * @param student 新学生的信息
     */
    @ApiOperation("创建新学生")
    @AuditLogRecord(action = "Create Student", resource = "Student")
    @PostMapping
    public R<Void> create(@RequestBody Student student) {
        log.info("Create Student = " + student);
        studentService.create(student);
        return R.ok();
    }

    /**
     * 更新学生信息
     *
     * @param student 更新后学生的信息
     */
    @ApiOperation("更新学生信息")
    @AuditLogRecord(action = "Update Student", resource = "Student")
    @PutMapping
    public R<Void> update(@RequestBody Student student) {
        log.info("Update Student = " + student);
        studentService.update(student);
        return R.ok();
    }

    /**
     * 删除学生
     *
     * @param id 要删除的学生的ID
     */
    @ApiOperation("删除学生")
    @AuditLogRecord(action = "Delete Student", resource = "Student")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        log.info("Delete Student By ID = " + id);
        studentService.delete(id);
        return R.ok();
    }

    /**
     * 根据用户ID获取学生信息
     *
     * @param userId 用户的ID
     * @return 对应用户的学生信息
     */
    @ApiOperation("根据UserID获取学生信息")
    @AuditLogRecord(action = "Get Student by UserId", resource = "Student")
    @GetMapping("/user/{userId}")
    public Student getByUserId(@PathVariable Long userId) {
        return studentService.getByUserId(userId);
    }
}
