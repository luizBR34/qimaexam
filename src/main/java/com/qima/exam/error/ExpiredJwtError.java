package com.qima.exam.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExpiredJwtError extends ResponseStatusException {
    public ExpiredJwtError() {
        super(HttpStatus.UNAUTHORIZED, "Token expired");
    }
}
