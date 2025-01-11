package com.qima.exam.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoBearerTokenError extends ResponseStatusException {
    public NoBearerTokenError() {
        super(HttpStatus.BAD_REQUEST, "No bearer token");
    }
}
