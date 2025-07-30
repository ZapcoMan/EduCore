package com.example.modules.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户信息类，继承自Account类
 * 该类主要用于表示系统中的用户，包含用户的详细信息
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Account {
    // 用户ID
    private Integer id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 角色
    private String role;
    // 用户姓名
    private String name;

    /**
     * 获取用户令牌
     *
     * @return 用户令牌字符串
     */
    @Override
    public String getToken() {
        return token;
    }

    /**
     * 设置用户令牌
     *
     * @param token 用户令牌字符串
     */
    @Override
    public void setToken(String token) {
        this.token = token;
    }

    // 用户令牌
    private String token;

    // 用户头像URL
    private String avatar;

    /**
     * 获取用户头像URL
     *
     * @return 用户头像的URL字符串
     */
    @Override
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置用户头像URL
     *
     * @param avatar 用户头像的URL字符串
     */
    @Override
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
