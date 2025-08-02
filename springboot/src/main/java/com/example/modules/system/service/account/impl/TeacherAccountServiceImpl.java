package com.example.modules.system.service.account.impl;

import com.example.modules.system.entity.Account;
import com.example.modules.system.entity.User;
import com.example.enums.RoleEnum;
import com.example.common.exception.CustomerException;
import com.example.modules.system.mapper.UserMapper;
import com.example.modules.system.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教师账户服务实现类
 * 实现了AccountService接口，用于处理教师账户相关的业务逻辑
 */
@Service
public class TeacherAccountServiceImpl implements AccountService {

    @Resource
    private UserMapper userMapper;

    /**
     * 获取角色枚举值
     *
     * @return RoleEnum.TEACHER 教师角色枚举
     */
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

    /**
     * 查询所有账户信息
     *
     * @return List<Account> 账户信息列表
     */
    @Override
    public List<Account> selectAll() {
        return List.of();
    }

    /**
     * 插入账户信息
     *
     * @param account 账户信息
     */
    @Override
    public void insert(Account account) {

    }

    /**
     * 根据ID更新账户信息
     *
     * @param account 账户信息
     */
    @Override
    public void updateById(Account account) {

    }

    /**
     * 根据ID删除账户信息
     *
     * @param id 账户ID
     */
    @Override
    public void deleteById(String id) {

    }

    /**
     * 教师登录验证
     *
     * @param account 包含用户名和密码的账户信息
     * @return Account 登录成功的账户信息
     * @throws CustomerException 如果教师账户不存在或密码错误
     */
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

    /**
     * 更新教师密码
     *
     * @param account 包含新密码信息的账户对象
     */
    @Override
    public void updatePassword(Account account) {
        userMapper.updatePassword(account.getUsername(), account.getNewpassword());
    }
}
