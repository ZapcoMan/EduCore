package com.example.modules.system.controller;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.common.R;
import com.example.modules.system.dto.ConfirmDto;
import com.example.modules.system.entity.Account;
import com.example.modules.system.service.AccountService;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.auth0.jwt.JWT;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/qrcode")
public class LoginQrCodeController {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Resource
    private List<AccountService> accountServices;
    private static final Log log = LogFactory.getLog(AuditLogController.class);
    // 生成二维码 UUID
    @GetMapping("/generate")
    public R generateQrCode() {
        String uuid = UUID.randomUUID().toString();
        String key = "login:uuid:" + uuid;
        Map<String, Object> data = new HashMap<>();
        data.put("status", "pending");
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(data), 5, TimeUnit.MINUTES);
        return R.success(uuid);
    }

    // 轮询二维码状态
    @GetMapping("/status/{uuid}")
    public R checkStatus(@PathVariable String uuid) {
        String key = "login:uuid:" + uuid;
        String json = redisTemplate.opsForValue().get(key);
        if (json == null) return R.error("二维码已过期");
        Map<String, Object> data = JSONUtil.toBean(json, Map.class);
        return R.success(data);
    }

    // 手机扫码确认登录
    @PostMapping("/confirm")
    public R confirmLogin(@RequestBody ConfirmDto dto) {
        log.info("确认登录：" + dto);
        String key = "login:uuid:" + dto.getUuid();
        String json = redisTemplate.opsForValue().get(key);
        if (json == null) return R.error("二维码已过期");

        AccountService matched = accountServices.stream()
                .filter(service -> service.getRole().getCode().equals(dto.getRole()))
                .findFirst().orElse(null);
        if (matched == null) return R.error("角色非法");

        Account dbAccount = matched.login(new Account(dto.getUsername(), dto.getPassword(), dto.getRole()));
        if (dbAccount == null) return R.error("账号或密码错误");

        // 构造 Token
        String token = JWT.create()
                .withAudience(dbAccount.getId() + "-" + dbAccount.getRole())
                .sign(Algorithm.HMAC256(dbAccount.getPassword()));
        dbAccount.setToken(token);

        // 回写 Redis
        Map<String, Object> result = new HashMap<>();
        result.put("status", "confirmed");
        result.put("account", dbAccount);
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(result), 5, TimeUnit.MINUTES);

        return R.success(dbAccount);
    }
}