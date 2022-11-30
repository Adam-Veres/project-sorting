package com.epam.exceptions;


import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistsException extends AuthenticationException {
    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message,cause);
    }
}
