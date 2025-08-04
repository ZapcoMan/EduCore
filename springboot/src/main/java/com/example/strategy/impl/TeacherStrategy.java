package com.example.strategy.impl;

import com.example.modules.system.dto.LoginResult;
import com.example.modules.system.entity.Account;
import com.example.enums.RoleEnum;
import com.example.modules.system.service.UserService;
import com.example.strategy.RoleStrategy;
import com.example.common.utils.JwtUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 教师角色策略实现类
 * 实现了RoleStrategy接口，提供教师角色特有的业务逻辑处理
 */
@Slf4j
@Component
public class TeacherStrategy implements RoleStrategy {

    @Resource
    private UserService teacherService;

    /**
     * 获取教师角色标识
     * @return 角色标识字符串"TEACHER"
     */
    @Override
    public String getRole() {
        return RoleEnum.TEACHER.getCode(); // "TEACHER"
    }

    /**
     * 教师登录处理
     * 验证教师账户信息并生成访问令牌
     * @param account 账户信息
     * @return LoginResult 登录结果，包含账户信息和访问令牌
     */
    @Override
    public LoginResult login(Account account) {
        Account teacher = teacherService.login(account);
        String token = JwtUtil.generateAccessToken(String.valueOf(teacher.getId()), List.of(teacher.getRole()));
        return new LoginResult(teacher, token);
    }

    /**
     * 更新教师密码
     * @param account 包含新密码的账户信息
     */
    @Override
    public void updatePassword(Account account) {
        teacherService.updatePassword(account);
    }

    /**
     * 教师注册处理
     * 教师不支持自助注册，抛出UnsupportedOperationException异常
     * @param account 账户信息
     * @throws UnsupportedOperationException 教师不支持自助注册
     */
    @Override
    public void register(Account account) {
        log.info("教师账号不支持自助注册，请从后台添加");
        throw new UnsupportedOperationException("教师账号不支持自助注册，请从后台添加");
    }

    /**
     * 根据用户ID查询教师账户信息
     * @param userId 用户ID
     * @return Account 教师账户信息
     */
    @Override
    public Account selectById(String userId) {
        return teacherService.selectById(userId);
    }
}
