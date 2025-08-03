package com.example.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.common.config.JwtProperties;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
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

    public static String generateAccessToken(String userId, List<String> roles) {
        return JWT.create()
                .withSubject(userId)
                .withClaim("roles", roles)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpire))
                .sign(algorithm);
    }

    public static String generateRefreshToken(String userId) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpire))
                .sign(algorithm);
    }

    public static DecodedJWT verifyToken(String token) {
        return verifier.verify(token);
    }

    public static String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    public static long getRemainingTime(String token) {
        try {
            DecodedJWT jwt = verifyToken(token);
            return jwt.getExpiresAt().getTime() - System.currentTimeMillis();
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean isTokenValid(String token) {
        try {
            verifyToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
