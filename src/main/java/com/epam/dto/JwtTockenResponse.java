package com.epam.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class JwtTockenResponse implements Serializable {

  @Serial private static final long serialVersionUID = -8091879091924046844L;
  private final String jwtToken;

  public JwtTockenResponse(final String jwtToken) {
    this.jwtToken = jwtToken;
  }
}
