package com.example.modules.system.controller;

import com.example.common.annotation.AuditLogRecord;
import com.example.common.result.R;

import com.example.core.controller.BaseController;
import com.example.core.service.BaseService;
import com.example.modules.system.entity.User;
import com.example.modules.system.service.impl.UserServiceImpl;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户控制器类，处理与用户相关的RESTful API请求
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User, Integer> {

    /**
     * 注入用户服务层接口，用于执行用户相关的业务逻辑
     */
    @Resource
    UserServiceImpl userServiceImpl;

    protected UserController(BaseService<User, Integer> baseService) {
        super(baseService);
    }

    /**
     * 添加新用户
     *
     * @param user 待添加的用户对象，通过请求体接收
     * @return 返回添加操作的结果
     */
    @ApiOperation("添加新用户")
    @AuditLogRecord(action = "添加新用户", resource = "用户")
    @PostMapping("/add")
    public R<Void> add(@RequestBody User user) {
        userServiceImpl.add(user);
        return R.ok();
    }

    /**
     * 更新用户信息
     *
     * @param user 待更新的用户对象，通过请求体接收
     * @return 返回更新操作的结果
     */
    @AuditLogRecord(action = "更新用户信息", resource = "用户")
    @ApiOperation("更新用户信息")
    @PutMapping("/update")
    public R<Void> update(@RequestBody User user) {
        userServiceImpl.update(user);
        return  R.ok();
    }

    /**
     * 根据用户ID删除用户
     *
     * @param id 用户ID，通过URL参数接收
     * @return 返回删除操作的结果
     */

    @AuditLogRecord(action = "删除用户", resource = "用户")
    @ApiOperation("根据用户ID删除用户")
    @DeleteMapping("/delete/{id}")
    public R<Void> delete(@PathVariable Integer id) {
        userServiceImpl.deleteById(id);
        return  R.ok();
    }

    /**
     * 批量删除用户
     *
     * @param list 待删除的用户列表，通过请求体接收
     * @return 返回批量删除操作的结果
     */
    @AuditLogRecord(action = "批量删除用户", resource = "用户")
    @ApiOperation("批量删除用户")
    @DeleteMapping("/deleteBatch")
    public R<Void> deleteBatch(@RequestBody List<User> list) {
        userServiceImpl.deleteBatch(list);
        return  R.ok();
    }

    /**
     * 查询所有用户信息
     *
     * @return 返回所有用户信息列表
     */
    @AuditLogRecord(action = "查询所有用户", resource = "用户")
    @ApiOperation("查询所有用户")
    @GetMapping("/selectAll")
    public R<Void> selectAll() {
        List<User> userList = userServiceImpl.selectAll();
        return  R.ok();
    }

    /**
     * 分页查询用户信息
     *
     * @param pageNum 页码，默认值为1
     * @param pageSize 每页记录数，默认值为10
     * @param user 用户对象，用于模糊查询
     * @return 返回分页查询结果，包含用户信息
     */
    @AuditLogRecord(action = "分页查询用户信息", resource = "用户")
    @ApiOperation("分页查询用户信息")
    @GetMapping("/selectPage")
    public R<Object> selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             User user) {
        return  R.ok().data("pageInfo", userServiceImpl.selectPage(pageNum, pageSize, user));  // 返回的是分页的对象
    }

}
