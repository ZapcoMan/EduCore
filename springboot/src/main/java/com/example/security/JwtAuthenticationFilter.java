package com.example.security;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.common.exception.CustomerException;
import com.example.modules.system.entity.Account;
import com.example.modules.system.service.AccountService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private List<AccountService> accountServices;

    private final Map<String, AccountService> serviceMap = new HashMap<>();

    private static final List<String> WHITE_LIST = Arrays.asList(
            "/auth/login", "/auth/register", "/auth/refresh-token",
            "/files/upload", "/files/download", "/favicon.ico"
    );


    /**
     * 初始化角色与对应服务映射
     */
    @PostConstruct
    public void initMap() {
        for (AccountService service : accountServices) {
            serviceMap.put(service.getRole().getCode(), service);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        // 白名单请求不需token校验
        for (String white : WHITE_LIST) {
            if (uri.startsWith(white)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 尝试从请求头或请求参数获取token
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)) {
            token = request.getParameter("token");
        }

        // 无token直接放行（可根据需求调整，通常应拒绝）
        if (StrUtil.isBlank(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 解析token中存储的用户ID和角色，格式为 "userId-role"
            String[] split = JWT.decode(token).getAudience().get(0).split("-");
            String userId = split[0];
            String role = split[1];

            AccountService service = serviceMap.get(role);
            if (service == null) throw new CustomerException("401", "非法角色");

            Account account = service.selectById(userId);
            if (account == null) throw new CustomerException("401", "用户不存在");

            // 使用账户密码作为签名密钥验证token
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(account.getPassword())).build();
            verifier.verify(token);

            // 设置Spring Security上下文，代表认证通过
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(account.getUsername(), null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // ===== 自动续签逻辑 =====
            // 如果token剩余有效期少于30分钟，则自动续签24小时
            if (shouldRenewToken(token, 30 * 60 * 1000)) {
                String newToken = createToken(userId + "-" + role, account.getPassword(), 24);
                response.setHeader("Renew-Token", newToken);
            }

        } catch (Exception ignored) {
            // 验证失败则不设置认证，继续执行过滤链
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 判断Token是否即将过期，剩余时间小于阈值则返回true
     * @param token JWT Token字符串
     * @param threshold 续签阈值，单位毫秒
     * @return 是否需要续签
     */
    private boolean shouldRenewToken(String token, long threshold) {
        try {
            Date expiresAt = JWT.decode(token).getExpiresAt();
            return expiresAt != null && (expiresAt.getTime() - System.currentTimeMillis() < threshold);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 生成新的JWT Token
     * @param audience 自定义数据（例如 userId-role）
     * @param secret 签名密钥，通常是用户密码
     * @param hours 过期时间（小时）
     * @return 新JWT Token字符串
     */
    private String createToken(String audience, String secret, int hours) {
        return JWT.create()
                .withAudience(audience)
                .withExpiresAt(DateUtil.offsetHour(new Date(), hours))
                .sign(Algorithm.HMAC256(secret));
    }
}
