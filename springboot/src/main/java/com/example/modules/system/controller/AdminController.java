package com.example.modules.system.controller;

import com.example.common.annotation.AuditLogRecord;
import com.example.common.result.R;
import com.example.modules.system.entity.Admin;
import com.example.modules.system.service.impl.AdminServiceImpl;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Admin控制器类，负责处理与管理员相关的RESTful API请求
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    /**
     * 注入Admin服务类，用于处理管理员相关的业务逻辑
     */
    @Resource
    AdminServiceImpl adminServiceImpl;



    /**
     * 处理管理员用户添加请求的方法
     * <p>
     * 该方法通过接收一个Admin对象作为参数，实现管理员用户的添加操作
     * 使用HTTP POST请求方式，请求体中的数据被直接映射到Admin对象中
     *
     * @param admin 包含管理员用户信息的对象，包括用户名、密码等
     * @return 返回一个R对象，表示操作结果状态
     */
    @ApiOperation("添加管理员")
    @AuditLogRecord(action = "添加管理员", resource = "管理员")
    @PostMapping("/add")
    public R add(@RequestBody Admin admin) {
        // 调用AdminServiceImpl的add方法，执行管理员用户添加操作
        adminServiceImpl.add(admin);
        // 返回操作成功的结果状态
        return R.ok();
    }

    /**
     * 更新管理员信息
     * @param admin 前端传入的管理员对象，通过JSON格式传递
     * @return 返回结果对象，表示更新操作是否成功
     */
    @ApiOperation("更新管理员信息")
    @AuditLogRecord(action = "更新管理员信息", resource = "管理员")
    @PutMapping("/update")
    public R update(@RequestBody Admin admin) {
        adminServiceImpl.update(admin);
        return R.ok();
    }

    /**
     * 根据ID删除管理员
     * @param id 要删除的管理员的ID，通过URL路径参数传递
     * @return 返回结果对象，表示删除操作是否成功
     */
    @ApiOperation("根据ID删除管理员")
    @AuditLogRecord(action = "删除管理员", resource = "管理员")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Integer id) {
        adminServiceImpl.deleteById(id);
        return R.ok();
    }

    /**
     * 批量删除管理员
     * @param list 前端传入的管理员对象列表，通过JSON数组格式传递
     * @return 返回结果对象，表示批量删除操作是否成功
     */
    @ApiOperation("批量删除管理员")
    @DeleteMapping("/deleteBatch")
    @AuditLogRecord(action = "批量删除管理员", resource = "管理员")
    public R deleteBatch(@RequestBody List<Admin> list) {
        adminServiceImpl.deleteBatch(list);
        return R.ok();
    }

    /**
     * 查询所有管理员
     * @return 返回结果对象，包含所有管理员信息的列表
     */
    @ApiOperation("查询所有管理员")
    @AuditLogRecord(action = "查询所有管理员", resource = "管理员")
    @GetMapping("/selectAll")
    public R selectAll() {
        return R.ok().data("adminList", adminServiceImpl.selectAll());
    }

    /**
     * 分页查询管理员
     * @param pageNum 当前的页码，默认值为1
     * @param pageSize 每页的个数，默认值为10
     * @param admin 管理员对象，可以包含查询条件
     * @return 返回结果对象，包含分页查询结果的PageInfo对象
     */
    @ApiOperation("分页查询管理员")
    @AuditLogRecord(action = "分页查询管理员", resource = "管理员")
    @GetMapping("/selectPage")
    public R selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             Admin admin) {
        return R.ok().data("pageInfo", adminServiceImpl.selectPage(pageNum, pageSize, admin));
    }

}
