package com.example.exception;

import org.hibernate.service.spi.ServiceException;

public class UnauthorizedException extends ServiceException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
