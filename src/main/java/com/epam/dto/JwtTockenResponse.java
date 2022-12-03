package com.epam.dto;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;


@Getter
public class JwtTockenResponse implements Serializable {

	@Serial
	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwtToken;

	public JwtTockenResponse(String jwtToken) {
		this.jwtToken = jwtToken;
	}
}