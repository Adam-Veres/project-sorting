package com.epam.dto;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

@Getter
public final class JwtControllersResponseMessage {
  private final Set<String> message;

  public JwtControllersResponseMessage(final BindException e) {
    this.message =
        e.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toSet());
  }

  public JwtControllersResponseMessage(final Exception e) {
    this(e.getMessage());
  }

  public JwtControllersResponseMessage(final String message) {
    this.message = Collections.singleton(message);
  }
}
