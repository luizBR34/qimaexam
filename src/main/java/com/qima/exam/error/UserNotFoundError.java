package com.qima.exam.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundError extends ResponseStatusException {
    public UserNotFoundError() {
        super(HttpStatus.BAD_REQUEST, "User not found!");
    }
}
