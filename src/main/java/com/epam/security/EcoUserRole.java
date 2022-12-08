package com.epam.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.security.Authority.AUTHORITY_ANONYMOUS;
import static com.epam.security.Authority.AUTHORITY_USER;
import static com.epam.security.Authority.AUTHORITY_RATING;
import static com.epam.security.Authority.AUTHORITY_SERVICE;



public enum EcoUserRole {
    ANONYMOUS(AUTHORITY_ANONYMOUS),
    @JsonProperty("User")
    USER(AUTHORITY_ANONYMOUS,AUTHORITY_USER,AUTHORITY_RATING),
    @JsonProperty("Service")
    SERVICE(AUTHORITY_ANONYMOUS, AUTHORITY_USER, AUTHORITY_SERVICE);

    private final List<Authority> authorities;

    EcoUserRole(final Authority ... authorities) {
        this.authorities = Arrays.asList(authorities);
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return authorities
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toSet());
    }
}
