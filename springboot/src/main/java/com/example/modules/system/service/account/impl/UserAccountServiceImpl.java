package com.example.modules.system.service.account.impl;

import com.example.modules.system.entity.Account;
import com.example.modules.system.entity.User;
import com.example.enums.RoleEnum;
import com.example.modules.system.service.AccountService;
import com.example.modules.system.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements AccountService {



    @Resource
    private UserService userService;
    @Override
    public RoleEnum getRole() {
        return RoleEnum.USER;
    }

    @Override
    public Account selectById(String id) {
        return userService.selectById(id);
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
    public void register(User user) {
        userService.insert(user);
    }
}