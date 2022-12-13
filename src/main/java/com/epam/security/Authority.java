package com.epam.security;

public enum Authority {
  AUTHORITY_ANONYMOUS("perm:anonymous"),
  AUTHORITY_USER("perm:user"),
  AUTHORITY_SERVICE("perm:service"),
  AUTHORITY_RATING("perm:rating");

  public static final String HAS_USER_AUTHORITY = "hasAuthority('perm:user')";
  public static final String HAS_SERVICE_AUTHORITY = "hasAuthority('perm:service')";
  public static final String HAS_RATING_AUTHORITY = "hasAuthority('perm:rating')";

  private final String authority;

  Authority(final String authority) {
    this.authority = authority;
  }

  public String getAuthority() {
    return authority;
  }
}
