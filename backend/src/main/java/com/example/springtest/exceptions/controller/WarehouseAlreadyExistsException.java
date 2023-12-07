package com.example.springtest.exceptions.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Warehouse already exists!")
public class WarehouseAlreadyExistsException extends RuntimeException {
    public WarehouseAlreadyExistsException() {
        super();
    }

    public WarehouseAlreadyExistsException(String message) {
        super(message);
    }

    public WarehouseAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
