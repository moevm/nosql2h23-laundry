package com.example.springtest.exceptions.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Branch already exists!")
public class BranchAlreadyExistsException extends RuntimeException {
    public BranchAlreadyExistsException() {
        super();
    }

    public BranchAlreadyExistsException(String message) {
        super(message);
    }

    public BranchAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
