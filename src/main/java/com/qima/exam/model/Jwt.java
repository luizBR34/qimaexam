package com.qima.exam.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Getter
public class Jwt {

    private final String token;
    private final Long userId;
    private final LocalDateTime issueAt;
    private final LocalDateTime expiration;

    private Jwt(String token, Long userId, LocalDateTime issueAt, LocalDateTime expiration) {
        this.token = token;
        this.userId = userId;
        this.issueAt = issueAt;
        this.expiration = expiration;
    }

    public static Jwt of(Long userId, Long validityInMinutes, String secretKey) {

        var issueAt = Instant.now();
        var expiration = issueAt.plus(validityInMinutes, ChronoUnit.MINUTES);
        String encodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        SecretKey key = Keys.hmacShaKeyFor(encodedSecretKey.getBytes());
        return new Jwt(
                Jwts.builder()
                    .claim("user_id", userId)
                    .setIssuedAt(Date.from(issueAt))
                    .setExpiration(Date.from(expiration))
                    .signWith(key, SignatureAlgorithm.HS256)
                .compact(),
                userId,
                LocalDateTime.ofInstant(issueAt, ZoneId.systemDefault()),
                LocalDateTime.ofInstant(expiration, ZoneId.systemDefault()));
    }

    public static Jwt from(String token, String secretKey) {
        SecretKey key = Keys.hmacShaKeyFor(
                Base64.getEncoder()
                        .encodeToString(secretKey.getBytes(StandardCharsets.UTF_8))
                        .getBytes()
        );
        var claims = (Claims) Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parse(token)
                .getBody();
        var userId = claims.get("user_id", Long.class);
        var issueAt = claims.getIssuedAt();
        var expiration = claims.getExpiration();
        return new Jwt(
                token,
                userId,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(issueAt.getTime()), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(Instant.ofEpochMilli(expiration.getTime()), ZoneId.systemDefault())
        );
    }
}
