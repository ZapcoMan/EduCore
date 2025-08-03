package com.example.modules.system.controller;

import com.example.common.result.R;
import com.example.common.utils.JwtUtil;
import com.example.common.utils.RedisUtil;
import com.example.modules.system.dto.LoginResult;
import com.example.modules.system.entity.Account;
import com.example.strategy.Context.RoleStrategyContext;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private RoleStrategyContext roleStrategyContext;
    @Resource
    private RedisUtil redisUtil;


    @Operation(summary = "登录")
    @PostMapping("/login")
    public R<LoginResult> login(@RequestBody Account account) {
        log.info("用户登录{}", account.getUsername());
        LoginResult result = roleStrategyContext.getStrategy(account.getRole()).login(account);
        return R.success(result);
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



    @PostMapping("/logout")
    public R<?> logout(HttpServletRequest request) {
        String token = JwtUtil.getTokenFromRequest(request);
        if (token != null && JwtUtil.isTokenValid(token)) {
            long expire = JwtUtil.getRemainingTime(token);
            redisUtil.set("blacklist:" + token, "true", expire, TimeUnit.MILLISECONDS);
        }
        return R.ok("登出成功");
    }
}
