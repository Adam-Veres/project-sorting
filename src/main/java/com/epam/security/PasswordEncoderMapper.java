package com.epam.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncoderMapper {

  private final PasswordEncoder passwordEncoder;

  @EncodedMapping
  public String encode(final String value) {
    return passwordEncoder.encode(value);
  }
}
