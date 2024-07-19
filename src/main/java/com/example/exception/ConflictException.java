package com.example.exception;

import org.hibernate.service.spi.ServiceException;

public class ConflictException extends ServiceException {
    public ConflictException(String message) {
        super(message);
    }
}
