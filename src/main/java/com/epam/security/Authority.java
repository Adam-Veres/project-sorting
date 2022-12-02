package com.epam.security;

public enum Authority {
    AUTHORITY_ANONYMOUS("perm:anonymous"),
    AUTHORITY_USER("perm:user"),
    AUTHORITY_SERVICE("perm:service");

    public static final String HAS_USER_AUTHORITY="hasAuthority('perm:user')";
    public static final String HAS_SERVICE_AUTHORITY="hasAuthority('perm:service')";

    private final String authority;

    Authority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}