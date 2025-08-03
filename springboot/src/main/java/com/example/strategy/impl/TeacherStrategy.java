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

@Slf4j
@Component
public class TeacherStrategy implements RoleStrategy {

    @Resource
    private UserService teacherService;

    @Override
    public String getRole() {
        return RoleEnum.TEACHER.getCode(); // "TEACHER"
    }

    @Override
    public LoginResult login(Account account) {
        Account teacher = teacherService.login(account);
        String token = JwtUtil.generateAccessToken(String.valueOf(teacher.getId()), List.of(teacher.getRole()));
        return new LoginResult(teacher, token);
    }

    @Override
    public void updatePassword(Account account) {
        teacherService.updatePassword(account);
    }

    @Override
    public void register(Account account) {
        log.info("教师账号不支持自助注册，请从后台添加");
        throw new UnsupportedOperationException("教师账号不支持自助注册，请从后台添加");
    }

    @Override
    public Account selectById(String userId) {
        return teacherService.selectById(userId);
    }
}
