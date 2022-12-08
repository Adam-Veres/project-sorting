package com.epam.exceptions;


import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistsException extends AuthenticationException {
    public UserAlreadyExistsException(final String message, final Throwable cause) {
        super(message,cause);
    }
}
