package com.example.strategy;


import com.example.modules.system.dto.LoginResult;
import com.example.modules.system.entity.Account;

public interface RoleStrategy {

    String getRole();

    LoginResult login(Account account);

    void updatePassword(Account account);

    void register(Account account);

    Account selectById(String userId);
}

