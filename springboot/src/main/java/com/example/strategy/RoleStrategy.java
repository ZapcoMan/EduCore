package com.example.strategy;

import com.example.modules.system.entity.Account;

public interface RoleStrategy {
    String getRole(); // 返回角色标识，如 ADMIN、USER、TEACHER

    Account login(Account account);

    void updatePassword(Account account);

    void register(Account account);
}
