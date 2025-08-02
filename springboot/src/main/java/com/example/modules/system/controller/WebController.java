package com.example.modules.system.controller;

import com.example.common.annotation.AuditLogRecord;
import com.example.common.result.R;
import com.example.enums.RoleEnum;
import com.example.modules.system.entity.Account;
import com.example.strategy.Context.RoleStrategyContext;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class WebController {


    @Resource
    private RoleStrategyContext roleStrategyContext;

    // 定义一个名为 hello 的接口，处理 GET 请求
    // 接口的路径，全局唯一的
    @GetMapping("/hello")
    public R<Object> hello() {
        // 返回一个成功的响应，包含消息 "hello"
        return R.ok().message("hello");  // 接口的返回值
    }

    /**
     * 登录接口，处理 POST 请求
     * 使用策略模式根据用户角色调用不同的登录方法
     *
     * @param account 用户账户信息，包含角色和登录所需的信息
     * @return 登录结果，包含成功或失败信息
     */
    @ApiOperation("登录")
    @AuditLogRecord(action = "登录", resource = "用户")
    @PostMapping("/login")
    public R<Account> login(@RequestBody Account account) {
        // 使用策略模式根据用户角色调用不同的登录方法
        return R.success(roleStrategyContext.getStrategy(account.getRole()).login(account));
    }

    /**
     * 用户注册接口，处理 POST 请求
     * 目前使用固定的 UserServiceImpl 执行注册逻辑
     * 未来可扩展为根据用户角色使用策略模式执行不同的注册逻辑
     *
     * @param account 用户账户信息，用于注册新用户
     * @return 注册结果，包含成功或失败信息
     * @throws UnsupportedOperationException 当注册策略分发功能未实现时抛出此异常
     */
    @ApiOperation("用户注册")
    @AuditLogRecord(action = "用户注册", resource = "用户")
    @PostMapping("/register")
    public R<Void> register(@RequestBody Account account) {
        // 注册逻辑保持原有 UserServiceImpl
        // 这里可以扩展成策略注册
        String role = account.getRole();
        // 只允许 USER 注册，其他角色禁止
        if (!RoleEnum.USER.getCode().equals(role)) {
            return R.error("仅允许学生注册，教师与管理员请联系教务处添加账号");
        }
        try {
            roleStrategyContext.getStrategy(account.getRole()).register(account);
        } catch (UnsupportedOperationException e) {
            log.warn(e.getMessage());
        }
        return R.ok();


    }

    /**
     * 更新密码接口，处理 POST 请求
     * 根据用户角色调用不同的更新密码方法
     *
     * @param account 用户账户信息，包含新密码和角色信息
     * @return 更新密码操作结果，包含成功或失败信息
     */
    @ApiOperation("更新密码")
    @AuditLogRecord(action = "更新密码", resource = "用户")
    @PostMapping("/updatePassword")
    public R<Void> updatePassword(@RequestBody Account account) {
        // 根据用户角色调用不同的更新密码方法
        roleStrategyContext.getStrategy(account.getRole()).updatePassword(account);
        return R.ok();
    }

}