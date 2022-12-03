package com.epam.dto;

import lombok.Getter;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public final class JwtControllersResponseMessage {
    private final Set<String> message;
    public JwtControllersResponseMessage(BindException e){
        this.message=e.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toSet());
    }
    public JwtControllersResponseMessage(Exception e){
        this(e.getMessage());
    }
    public JwtControllersResponseMessage(String message){
        this.message= Collections.singleton(message);
    }
}