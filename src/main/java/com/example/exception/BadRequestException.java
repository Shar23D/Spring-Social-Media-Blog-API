package com.example.exception;

import org.hibernate.service.spi.ServiceException;

public class BadRequestException extends ServiceException {
    public BadRequestException(String message) {
        super(message);
    }
}
