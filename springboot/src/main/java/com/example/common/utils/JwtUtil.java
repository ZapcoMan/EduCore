package com.example.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.common.config.JwtProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private static String secret;
    private static long accessTokenExpire;
    private static long refreshTokenExpire;

    private static Algorithm algorithm;
    private static JWTVerifier verifier;

    @Autowired
    private JwtProperties jwtProperties;

    @PostConstruct
    public void init() {
        secret = jwtProperties.getSecret();
        accessTokenExpire = jwtProperties.getAccessTokenExpire();
        refreshTokenExpire = jwtProperties.getRefreshTokenExpire();

        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).build();
    }

    // 生成 Access Token
    public static String generateAccessToken(String userId, List<String> roles) {
        return JWT.create()
                .withSubject(userId)
                .withClaim("roles", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpire))
                .sign(algorithm);
    }

    // 生成 Refresh Token
    public static String generateRefreshToken(String userId) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpire))
                .sign(algorithm);
    }

    // 验证 Token
    public static DecodedJWT verifyToken(String token) {
        return verifier.verify(token);
    }
}
