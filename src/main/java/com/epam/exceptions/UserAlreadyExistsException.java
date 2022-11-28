package com.epam.exceptions;


public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message,cause);
    }
}
