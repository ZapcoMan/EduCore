package com.example.modules.system.service;

import com.example.core.service.BaseService;
import com.example.modules.system.entity.Account;
import com.example.modules.system.entity.User;
import com.example.enums.RoleEnum;
/**
 * AccountService接口定义了账户服务的通用操作，包括登录、密码更新和根据角色的不同行为
 */
public interface AccountService extends BaseService<Account, String> {
    /**
     * 获取服务支持的角色
     *
     * @return RoleEnum类型，表示服务支持的角色
     */
    RoleEnum getRole();  // 标明服务支持哪个角色

    /**
     * 根据账户ID选择账户信息
     *
     * @param id 账户的唯一标识符
     * @return Account类型，表示查询到的账户信息
     */
    Account selectById(String id);

    /**
     * 更新账户的密码
     *
     * @param account 包含新密码的账户对象
     */
    void updatePassword(Account account);

    /**
     * 登录操作，验证账户信息
     *
     * @param account 包含登录信息的账户对象
     * @return Account类型，表示登录成功的账户信息
     */
    Account login(Account account);

    /**
     * 注册新用户默认实现抛出异常，因为不是所有角色都支持注册操作
     *
     * @param user 用户对象，包含注册信息
     * @throws UnsupportedOperationException 如果该角色不支持注册操作
     */
    default void register(User user) {
        throw new UnsupportedOperationException("该角色不支持注册");
    }
}
