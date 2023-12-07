package com.example.springtest.exceptions.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such User")
public class NoSuchWarehouseException extends RuntimeException {
    public NoSuchWarehouseException() {
        super();
    }

    public NoSuchWarehouseException(String message) {
        super(message);
    }

    public NoSuchWarehouseException(String message, Throwable cause) {
        super(message, cause);
    }
}
