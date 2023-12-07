package com.example.springtest.exceptions.controller;


public class ServiceAlreadyExistsException extends RuntimeException {
    public ServiceAlreadyExistsException() {
        super();
    }

    public ServiceAlreadyExistsException(String message) {
        super(message);
    }

    public ServiceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
