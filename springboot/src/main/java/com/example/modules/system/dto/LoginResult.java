package com.example.modules.system.dto;

import com.example.modules.system.entity.Account;
import lombok.Data;

@Data
public class LoginResult {
    private Account account;
    private String token;

    public LoginResult(Account account, String token) {
        this.account = account;
        this.token = token;
    }


}
