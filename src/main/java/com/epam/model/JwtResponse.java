package com.epam.model;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;


@Getter
public class JwtResponse implements Serializable {

	@Serial
	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwtToken;

	public JwtResponse(String jwtToken) {
		this.jwtToken = jwtToken;
	}
}