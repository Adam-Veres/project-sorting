package com.epam.security;

public enum Authority {
    AUTHORITY_ANONYMOUS("perm:default"),
    AUTHORITY_USER("perm:user"),
    AUTHORITY_SERVICE("perm:service");

    private final String authority;

    Authority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
