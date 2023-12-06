package com.example.springtest.exceptions.controller;

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
