package com.example.springtest.exceptions.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Branch")
public class NoSuchBranchException extends RuntimeException {
    public NoSuchBranchException() {
        super();
    }

    public NoSuchBranchException(String message) {
        super(message);
    }

    public NoSuchBranchException(String message, Throwable cause) {
        super(message, cause);
    }
}
