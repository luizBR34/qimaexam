package com.qima.exam.service;

import com.qima.exam.error.ExpiredJwtError;
import com.qima.exam.error.UserNotFoundError;
import com.qima.exam.model.Jwt;
import com.qima.exam.model.User;
import com.qima.exam.repository.UserRepo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final String accessTokenSecret;
    private final String refreshTokenSecret;

    public AuthService(UserRepo repository,
                       PasswordEncoder passwordEncoder,
                       @Value("${application.security.access-token-secret}") String accessTokenSecret,
                       @Value("${application.security.refresh-token-secret}") String refreshTokenSecret) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.accessTokenSecret = accessTokenSecret;
        this.refreshTokenSecret = refreshTokenSecret;
    }

    public User getUserFromToken(String token) {
        Jwt userId = null;
        try {
            userId = Jwt.from(token, accessTokenSecret);
        } catch (ExpiredJwtException | MalformedJwtException e) {
            throw new ExpiredJwtError();
        }
        return repository.findById(userId.getUserId()).orElseThrow(UserNotFoundError::new);
    }
}
