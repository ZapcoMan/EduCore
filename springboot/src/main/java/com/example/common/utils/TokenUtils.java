package com.example.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.modules.system.entity.Account;
import com.example.modules.system.service.impl.AdminServiceImpl;
import com.example.modules.system.service.impl.UserServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

/**
 * Token工具类，提供Token生成和用户信息获取功能
 */
@Component
public class TokenUtils {

    // 注入AdminServiceImpl和UserServiceImpl用于获取用户信息
    @Resource
    AdminServiceImpl adminServiceImpl;
    @Resource
    UserServiceImpl userServiceImpl;

    // 静态变量用于存储Service实例，以便在静态方法中使用
    static AdminServiceImpl staticAdminServiceImpl;;
    static UserServiceImpl staticUserServiceImpl;

    /**
     * 在Spring Boot工程启动后初始化静态变量
     */
    @PostConstruct
    public void init() {
        staticAdminServiceImpl = adminServiceImpl;
        staticUserServiceImpl = userServiceImpl;
    }

    /**
     * 生成Token
     * @param data Token中的载荷数据，这里包含用户ID和角色，用"-"分隔
     * @param sign Token的密钥，用于签名
     * @return 生成的Token字符串
     */
    public static String createToken(String data, String sign) {
        return JWT.create().withAudience(data) // 将 userId-role 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetDay(new Date(), 1)) // 1天后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥, HMAC256算法加密
    }

    /**
     * 获取当前登录的用户信息
     * @return 当前登录的Account对象，如果没有登录或Token无效，则返回null
     */
    public static Account getCurrentUser() {
        // 获取当前请求的HttpServletRequest对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 优先从请求头中获取Token，如果不存在，则从请求参数中获取
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }
        // 解析Token获取载荷数据，并根据角色类型调用对应的服务获取用户信息
        String audience = JWT.decode(token).getAudience().get(0);
        String[] split = audience.split("-");
        String userId = split[0];
        String role = split[1];
        if ("ADMIN".equals(role)) {
            return staticAdminServiceImpl.selectById(userId);
        } else if ("USER".equals(role)) {
            return staticUserServiceImpl.selectById(userId);
        }
        return null;
    }

}
