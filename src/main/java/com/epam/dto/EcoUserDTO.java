package com.epam.dto;

import com.epam.security.EcoUserRole;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
public class EcoUserDTO {

  @NotBlank
  @Pattern(regexp = "^[a-zA-Z\\d]+$", message = "LoginError")
  @Size(min = 4, max = 24, message = "LoginSizeError")
  private String username;

  @NotBlank
  @Pattern(regexp = "^[a-zA-Z\\d]+$", message = "PasswordError")
  @Size(min = 9, max = 24, message = "PasswordSizeError")
  private String password;

  @NotBlank
  @Email(regexp = "^[a-zA-Z\\d.]+@[a-zA-Z\\d.]+$", message = "EmailError")
  @NotNull(message = "EmailError")
  private String email;

  @JsonAlias("role")
  @JsonProperty("role")
  private EcoUserRole userRole = EcoUserRole.ANONYMOUS;
}
