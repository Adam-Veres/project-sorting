package com.epam.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.security.Authority.*;


public enum EcoUserRole {
    ANONYMOUS(AUTHORITY_ANONYMOUS),
    @JsonProperty("User")
    USER(AUTHORITY_ANONYMOUS,AUTHORITY_USER),
    @JsonProperty("Service")
    SERVICE(AUTHORITY_ANONYMOUS, AUTHORITY_USER, AUTHORITY_SERVICE);

    private final List<Authority> authorities;

    EcoUserRole(Authority ... authorities) {
        this.authorities = Arrays.asList(authorities);
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return authorities
                .stream()
                .map(Authority::getAuthority)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
