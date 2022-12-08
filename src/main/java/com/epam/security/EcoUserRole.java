package com.epam.security;

import static com.epam.security.Authority.AUTHORITY_ANONYMOUS;
import static com.epam.security.Authority.AUTHORITY_SERVICE;
import static com.epam.security.Authority.AUTHORITY_USER;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@AllArgsConstructor
public enum EcoUserRole {
  ANONYMOUS(List.of(AUTHORITY_ANONYMOUS)),
  @JsonProperty("User")
  USER(List.of(AUTHORITY_ANONYMOUS, AUTHORITY_USER)),
  @JsonProperty("Service")
  SERVICE(List.of(AUTHORITY_ANONYMOUS, AUTHORITY_USER, AUTHORITY_SERVICE));

  private final List<Authority> authorities;

  public Set<SimpleGrantedAuthority> getAuthorities() {
    return authorities.stream()
        .map(Authority::getAuthority)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
  }
}
