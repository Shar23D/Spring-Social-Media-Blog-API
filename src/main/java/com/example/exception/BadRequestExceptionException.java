package com.example.service;

import org.hibernate.service.spi.ServiceException;

public class BadRequestExceptionException extends ServiceException {
    public BadRequestExceptionException(String message) {
        super(message);
    }
}
