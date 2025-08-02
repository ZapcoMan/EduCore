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

@Component
public class UserStrategy implements RoleStrategy {

    @Autowired
    private UserService userService;

    @Override
    public String getRole() {
        return RoleEnum.USER.getCode(); // "USER"
    }

    @Override
    public LoginResult login(Account account) {
        Account user = userService.login(account);
        String token = JwtUtil.generateAccessToken(String.valueOf(user.getId()), List.of(user.getRole()));
        return new LoginResult(user, token);
    }

    @Override
    public void updatePassword(Account account) {
        userService.updatePassword(account);
    }

    @Override
    public void register(Account account) {
        User user = new User();
        user.setUsername(account.getUsername());
        user.setPassword(account.getPassword());
        userService.register(user);
    }

    @Override
    public Account selectById(String userId) {
        return userService.selectById(userId);
    }
}
