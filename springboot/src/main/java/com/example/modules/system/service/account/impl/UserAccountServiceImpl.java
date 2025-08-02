package com.example.modules.system.service.account.impl;

import com.example.modules.system.entity.Account;
import com.example.modules.system.entity.User;
import com.example.enums.RoleEnum;
import com.example.modules.system.service.AccountService;
import com.example.modules.system.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户账户服务实现类
 * 实现了AccountService接口，用于处理普通用户账户相关的业务逻辑
 */
@Service
public class UserAccountServiceImpl implements AccountService {



    @Resource
    private UserService userService;

    /**
     * 获取角色枚举值
     *
     * @return RoleEnum.USER 普通用户角色枚举
     */
    @Override
    public RoleEnum getRole() {
        return RoleEnum.USER;
    }

    /**
     * 根据ID查询账户信息
     *
     * @param id 账户ID
     * @return Account 账户信息
     */
    @Override
    public Account selectById(String id) {
        return userService.selectById(id);
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
     * 用户登录验证
     *
     * @param account 包含用户名和密码的账户信息
     * @return Account 登录成功的账户信息
     */
    @Override
    public Account login(Account account) {
        return userService.login(account);
    }

    /**
     * 更新用户密码
     *
     * @param account 包含新密码信息的账户对象
     */
    @Override
    public void updatePassword(Account account) {
        userService.updatePassword(account);
    }

    /**
     * 用户注册
     *
     * @param user 用户信息
     */
    @Override
    public void register(User user) {
        userService.insert(user);
    }
}
