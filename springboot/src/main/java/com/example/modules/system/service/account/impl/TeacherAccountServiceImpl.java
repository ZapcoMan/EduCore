package com.example.modules.system.service.account.impl;

import com.example.modules.system.entity.Account;
import com.example.modules.system.entity.User;
import com.example.enums.RoleEnum;
import com.example.common.exception.CustomerException;
import com.example.modules.system.mapper.UserMapper;
import com.example.modules.system.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TeacherAccountServiceImpl implements AccountService {

    @Resource
    private UserMapper userMapper;

    @Override
    public RoleEnum getRole() {
        return RoleEnum.TEACHER;
    }

    /**
     * 根据账户ID选择账户信息
     *
     * @param id 账户的唯一标识符
     * @return Account类型，表示查询到的账户信息
     */
    @Override
    public Account selectById(String id) {
        return null;
    }

    @Override
    public Account login(Account account) {
        Account db = userMapper.selectByUsername(account.getUsername());
        if (db == null || !db.getRole().equalsIgnoreCase("TEACHER")) {
            throw new CustomerException("教师账户不存在");
        }
        if (!db.getPassword().equals(account.getPassword())) {
            throw new CustomerException("密码错误");
        }
        return db;
    }

    /**
     * 注册新用户默认实现抛出异常，因为不是所有角色都支持注册操作
     *
     * @param user 用户对象，包含注册信息
     * @throws UnsupportedOperationException 如果该角色不支持注册操作
     */
    @Override
    public void register(User user) {
        AccountService.super.register(user);
    }

    @Override
    public void updatePassword(Account account) {
        userMapper.updatePassword(account.getUsername(), account.getNewpassword());
    }
}