package com.example.modules.system.controller;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.common.result.R;
import com.example.modules.system.dto.ConfirmDto;
import com.example.modules.system.entity.Account;
import com.example.modules.system.service.AccountService;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/qrcode")
public class LoginQrCodeController  {


    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Resource
    private List<AccountService> accountServices;
    private static final Log log = LogFactory.getLog(LoginQrCodeController.class);



    /**
     * 生成登录二维码的UUID
     *
     * @return 返回包含UUID的响应结果
     */
    @ApiOperation("生成登录二维码")
    @GetMapping("/generate")
    public R<String> generateQrCode() {
        // 生成唯一标识符
        String uuid = UUID.randomUUID().toString();
        String key = "login:uuid:" + uuid;
        Map<String, Object> data = new HashMap<>();
        data.put("status", "pending");
        // 将二维码状态存储到Redis中，5分钟过期
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(data), 5, TimeUnit.MINUTES);
        return R.success(uuid);
    }

    /**
     * 检查二维码状态
     *
     * @param uuid 二维码唯一标识符
     * @return 返回包含二维码状态信息的响应结果
     */
    @ApiOperation("检查二维码状态")
    @GetMapping("/status/{uuid}")
    public R<Map<String, Object>> checkStatus(@PathVariable String uuid) {
        String key = "login:uuid:" + uuid;
        // 从Redis中获取二维码状态
        String json = redisTemplate.opsForValue().get(key);
        if (json == null) return R.error("二维码已过期");
        Map<String, Object> data = JSONUtil.toBean(json, Map.class);
        return R.success(data);
    }

    /**
     * 手机扫码确认登录
     *
     * @param dto 包含登录信息的数据传输对象，包括uuid、用户名、密码和角色
     * @return 返回登录结果，成功时返回包含用户信息和token的响应，失败时返回错误信息
     */
    @ApiOperation("手机扫码确认登录")
    @PostMapping("/confirm")
    public R<Account> confirmLogin(@RequestBody ConfirmDto dto) {
        log.info("确认登录：" + dto);
        String key = "login:uuid:" + dto.getUuid();
        String json = redisTemplate.opsForValue().get(key);
        if (json == null) return R.error("二维码已过期");

        // 查找匹配的角色服务
        AccountService matched = accountServices.stream()
                .filter(service -> service.getRole().getCode().equals(dto.getRole()))
                .findFirst().orElse(null);
        if (matched == null) return R.error("角色非法");

        // 执行登录验证
        Account dbAccount = matched.login(new Account(dto.getUsername(), dto.getPassword(), dto.getRole()));
        if (dbAccount == null) return R.error("账号或密码错误");

        // 构造 JWT Token
        String token = JWT.create()
                .withAudience(dbAccount.getId() + "-" + dbAccount.getRole())
                .sign(Algorithm.HMAC256(dbAccount.getPassword()));
        dbAccount.setToken(token);

        // 回写登录结果到Redis
        Map<String, Object> result = new HashMap<>();
        result.put("status", "confirmed");
        result.put("account", dbAccount);
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(result), 5, TimeUnit.MINUTES);

        return R.success(dbAccount);
    }

}