package com.qima.exam.model;

import java.time.LocalDateTime;

public record Token(String refreshToken, LocalDateTime issueAt, LocalDateTime expiredAt) {
}
