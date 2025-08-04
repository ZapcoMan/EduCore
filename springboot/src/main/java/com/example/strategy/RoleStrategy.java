package com.example.strategy;


import com.example.modules.system.dto.LoginResult;
import com.example.modules.system.entity.Account;

/**
 * 角色策略接口
 * 定义了不同角色用户的相关操作方法，实现角色特定的业务逻辑
 */
public interface RoleStrategy {

    /**
     * 获取角色标识
     * @return 角色标识字符串
     */
    String getRole();

    /**
     * 用户登录处理
     * @param account 账户信息
     * @return LoginResult 登录结果
     */
    LoginResult login(Account account);

    /**
     * 更新用户密码
     * @param account 账户信息
     */
    void updatePassword(Account account);

    /**
     * 用户注册处理
     * @param account 账户信息
     */
    void register(Account account);

    /**
     * 根据用户ID查询账户信息
     * @param userId 用户ID
     * @return Account 账户信息
     */
    Account selectById(String userId);
}
