package com.example.strategy.impl;

import com.example.modules.system.entity.Account;
import com.example.enums.RoleEnum;
import com.example.modules.system.service.AdminService;
import com.example.strategy.RoleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdminStrategy implements RoleStrategy {

    @Autowired
    private AdminService adminService;

    @Override
    public String getRole() {
        return RoleEnum.ADMIN.getCode(); // "ADMIN"
    }

    @Override
    public Account login(Account account) {
        return adminService.login(account);
    }

    @Override
    public void updatePassword(Account account) {
        adminService.updatePassword(account);
    }

    @Override
    public void register(Account account) {
        log.info("管理员账号不支持自助注册，请从后台添加");
        throw new UnsupportedOperationException("管理员账号不支持自助注册，请从后台添加");
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public Account selectById(String userId) {
        return null;
    }
}

