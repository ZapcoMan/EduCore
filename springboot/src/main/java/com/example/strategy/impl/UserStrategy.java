package com.example.strategy.impl;

import com.example.modules.system.dto.LoginResult;
import com.example.modules.system.entity.Account;
import com.example.modules.system.entity.User;
import com.example.enums.RoleEnum;
import com.example.modules.system.service.UserService;
import com.example.strategy.RoleStrategy;
import com.example.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 学生用户角色策略实现类
 * 实现了RoleStrategy接口，提供学生用户角色特有的业务逻辑处理
 */
@Component
public class UserStrategy implements RoleStrategy {

    @Autowired
    private UserService userService;

    /**
     * 获取学生用户角色标识
     * @return 角色标识字符串"USER"
     */
    @Override
    public String getRole() {
        return RoleEnum.USER.getCode(); // "USER"
    }

    /**
     * 学生用户登录处理
     * 验证学生用户账户信息并生成访问令牌
     * @param account 账户信息
     * @return LoginResult 登录结果，包含账户信息和访问令牌
     */
    @Override
    public LoginResult login(Account account) {
        Account user = userService.login(account);
        String token = JwtUtil.generateAccessToken(String.valueOf(user.getId()), List.of(user.getRole()));
        return new LoginResult(user, token);
    }

    /**
     * 更新学生用户密码
     * @param account 包含新密码的账户信息
     */
    @Override
    public void updatePassword(Account account) {
        userService.updatePassword(account);
    }

    /**
     * 学生用户注册处理
     * 将Account信息转换为User对象并调用UserService的注册方法
     * @param account 账户信息
     */
    @Override
    public void register(Account account) {
        User user = new User();
        user.setUsername(account.getUsername());
        user.setPassword(account.getPassword());
        userService.register(user);
    }

    /**
     * 根据用户ID查询学生用户账户信息
     * @param userId 用户ID
     * @return Account 学生用户账户信息
     */
    @Override
    public Account selectById(String userId) {
        return userService.selectById(userId);
    }
}
