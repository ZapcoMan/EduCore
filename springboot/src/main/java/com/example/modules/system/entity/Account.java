package com.example.modules.system.entity;


import lombok.*;

/**
 * Account类代表系统中的用户账户信息
 * 它封装了用户相关的各种属性，如用户名、密码、角色等
 * 该类使用了Lombok库的注解，以自动生成setter和getter方法，简化代码
 */
@NoArgsConstructor  // 生成无参构造函数
@AllArgsConstructor // 生成全参构造函数
@Data
public class Account {

    /**
     * 用户ID，唯一标识一个用户
     */
    private Integer id;

    /**
     * 用户名，用户登录时使用的标识
     */
    private String username;

    /**
     * 密码，用于验证用户身份
     */
    private String password;

    /**
     * 用户角色，决定了用户在系统中的权限级别
     */
    private String role;

    /**
     * 用户真实姓名，用于在系统中显示用户的真实身份信息
     */
    private String name;

    /**
     * 用户令牌，通常用于维持用户登录状态或API访问权限
     */
    private String token;

    /**
     * 用户头像的URL地址，用于展示用户的个性化信息
     */
    private String avatar;

    /**
     * 新密码，用于用户修改密码功能
     */
    private String newpassword;

    /**
     * 新密码的再次确认，确保用户在修改密码时正确无误地输入新密码
     */
    private String new2password;


    public Account(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
