package com.example.strategy.impl;

import com.example.modules.system.entity.Account;
import com.example.modules.system.entity.User;
import com.example.enums.RoleEnum;
import com.example.modules.system.service.UserService;
import com.example.strategy.RoleStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserStrategy implements RoleStrategy {

    @Autowired
    private UserService userService;

    @Override
    public String getRole() {
        return RoleEnum.USER.getCode(); // "USER"
    }

    @Override
    public Account login(Account account) {
        return userService.login(account);
    }

    @Override
    public void updatePassword(Account account) {
        userService.updatePassword(account);
    }

    @Override
    public void register(Account account) {
        // 新建一个Admin对象 从 account 中取出一个admin 对象来
        User user = new User();
        user.setUsername(account.getUsername());
        user.setPassword(account.getPassword());
        userService.register(user);
    }
}

