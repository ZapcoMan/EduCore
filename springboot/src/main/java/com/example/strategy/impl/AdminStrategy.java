package com.example.strategy.impl;

import com.example.modules.system.dto.LoginResult;
import com.example.modules.system.entity.Account;
import com.example.enums.RoleEnum;
import com.example.modules.system.service.AdminService;
import com.example.strategy.RoleStrategy;
import com.example.common.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 管理员角色策略实现类
 * 实现了RoleStrategy接口，提供管理员角色特有的业务逻辑处理
 */
@Slf4j
@Component
public class AdminStrategy implements RoleStrategy {

    @Autowired
    private AdminService adminService;

    /**
     * 获取管理员角色标识
     * @return 角色标识字符串"ADMIN"
     */
    @Override
    public String getRole() {
        return RoleEnum.ADMIN.getCode(); // "ADMIN"
    }

    /**
     * 管理员登录处理
     * 验证管理员账户信息并生成访问令牌
     * @param account 账户信息
     * @return LoginResult 登录结果，包含账户信息和访问令牌
     */
    @Override
    public LoginResult login(Account account) {
        Account admin = adminService.login(account);
        String token = JwtUtil.generateAccessToken(String.valueOf(admin.getId()), List.of(admin.getRole()));
        return new LoginResult(admin, token);
    }

    /**
     * 更新管理员密码
     * @param account 包含新密码的账户信息
     */
    @Override
    public void updatePassword(Account account) {
        adminService.updatePassword(account);
    }

    /**
     * 管理员注册处理
     * 管理员不支持自助注册，抛出UnsupportedOperationException异常
     * @param account 账户信息
     * @throws UnsupportedOperationException 管理员不支持自助注册
     */
    @Override
    public void register(Account account) {
        log.info("管理员账号不支持自助注册，请从后台添加");
        throw new UnsupportedOperationException("管理员账号不支持自助注册，请从后台添加");
    }

    /**
     * 根据用户ID查询管理员账户信息
     * @param userId 用户ID
     * @return Account 管理员账户信息
     */
    @Override
    public Account selectById(String userId) {
        return adminService.selectById(userId);
    }
}
