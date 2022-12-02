package com.epam.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = 5926468583005150707L;

	private String username;
	private String password;

}