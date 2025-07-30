package com.example.strategy.Context;



import com.example.enums.RoleEnum;import com.example.common.exception.CustomerException;import com.example.strategy.RoleStrategy;import org.springframework.stereotype.Component;import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoleStrategyContext {

    private final Map<String, RoleStrategy> strategyMap = new HashMap<>();

    public RoleStrategyContext(List<RoleStrategy> strategies) {
        for (RoleStrategy strategy : strategies) {
            strategyMap.put(strategy.getRole().toUpperCase(), strategy);
        }
    }

    public RoleStrategy getStrategy(String roleCode) {
        RoleEnum.fromCode(roleCode); // 强校验
        RoleStrategy strategy = strategyMap.get(roleCode.toUpperCase());
        if (strategy == null) {
            throw new CustomerException("暂未支持的角色类型: " + roleCode);
        }
        return strategy;
    }
}


