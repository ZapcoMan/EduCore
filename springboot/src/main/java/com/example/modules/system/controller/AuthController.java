package com.example.modules.system.controller;

import com.example.common.result.R;
import com.example.modules.system.entity.Account;
import com.example.strategy.Context.RoleStrategyContext;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private RoleStrategyContext roleStrategyContext;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public R<Account> login(@RequestBody Account account) {
        return R.success(roleStrategyContext.getStrategy(account.getRole()).login(account));
    }

    @Operation(summary = "刷新 Token")
    @PostMapping("/refresh-token")
    public R<String> refreshToken(HttpServletRequest request) {
        String oldToken = request.getHeader("token");
        if (oldToken == null || oldToken.isEmpty()) {
            return R.error("未提供旧 Token");
        }

        try {
            String newToken = roleStrategyContext.refreshToken(oldToken);
            return R.success(newToken).message("Token 已续签");
        } catch (Exception e) {
            log.warn("Token 刷新失败：{}", e.getMessage());
            return R.error("Token 刷新失败：" + e.getMessage());
        }
    }

    /**
     * 登出接口
     * @param request HTTP请求，可从请求头获取token
     * @return 注销结果
     */
    @Operation(summary = "登出")
    @PostMapping("/logout")
    public R<Void> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        // TODO: 若使用 Redis 等，加入 token 黑名单逻辑，防止注销后token继续使用
        // 当前为无状态认证，前端清除token即可，服务器无需额外处理
        log.info("用户请求登出，token: {}", token);
        return R.ok();
    }


}
