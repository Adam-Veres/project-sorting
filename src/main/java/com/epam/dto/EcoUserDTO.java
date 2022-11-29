package com.epam.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class EcoUserDTO {

	@Pattern(regexp = "^[a-zA-Z\\d]+$", message = "LoginError")
	@Size(min = 4, max = 24, message = "LoginSizeError")
	private String username;

	@Pattern(regexp = "^[a-zA-Z\\d]+$", message = "PasswordError")
	@Size(min = 9, max = 24, message = "PasswordSizeError")
	private String password;
}