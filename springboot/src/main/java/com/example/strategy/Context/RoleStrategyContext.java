package com.example.strategy.Context;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.common.exception.CustomerException;
import com.example.common.utils.JwtUtil;
import com.example.enums.RoleEnum;
import com.example.modules.system.entity.Account;
import com.example.strategy.RoleStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色策略上下文类
 * 用于管理和获取不同角色的策略实现，支持根据角色类型动态调用相应的业务逻辑
 * 同时提供Token刷新功能
 */
@Component
public class RoleStrategyContext {

    private final Map<String, RoleStrategy> strategyMap = new HashMap<>();

    /**
     * 构造函数，初始化策略映射表
     * 将所有RoleStrategy实现类按角色标识存入映射表中
     * @param strategies 所有RoleStrategy实现类的列表
     */
    public RoleStrategyContext(List<RoleStrategy> strategies) {
        for (RoleStrategy strategy : strategies) {
            strategyMap.put(strategy.getRole().toUpperCase(), strategy);
        }
    }

    /**
     * 根据角色代码获取对应的策略实现
     * @param roleCode 角色代码
     * @return RoleStrategy 对应的角色策略实现
     * @throws CustomerException 当角色代码不支持时抛出异常
     */
    public RoleStrategy getStrategy(String roleCode) {
        RoleEnum.fromCode(roleCode); // 强校验
        RoleStrategy strategy = strategyMap.get(roleCode.toUpperCase());
        if (strategy == null) {
            throw new CustomerException("暂未支持的角色类型: " + roleCode);
        }
        return strategy;
    }

    /**
     * 刷新 Token 的方法：
     * 1. 解析旧 token，提取用户 ID 和角色；
     * 2. 调用策略中的 selectById 重新获取账号信息；
     * 3. 用新的账号信息生成新的 Access Token。
     * @param oldToken 需要刷新的旧Token
     * @return String 新的访问Token
     * @throws CustomerException Token刷新失败时抛出异常
     */
    public String refreshToken(String oldToken) {
        try {
            DecodedJWT decodedJWT = JwtUtil.verifyToken(oldToken);
            String userId = decodedJWT.getSubject();
            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

            if (roles == null || roles.isEmpty()) {
                throw new CustomerException("Token 中缺少角色信息");
            }

            String role = roles.get(0).toUpperCase(); // 默认处理第一个角色
            RoleStrategy strategy = getStrategy(role);

            Account account = strategy.selectById(userId);
            if (account == null) {
                throw new CustomerException("用户不存在，无法续签");
            }

            return JwtUtil.generateAccessToken(
                    String.valueOf(account.getId()),
                    List.of(account.getRole())
            );
        } catch (Exception e) {
            throw new CustomerException("Token 刷新失败: " + e.getMessage(), e);
        }
    }
}
