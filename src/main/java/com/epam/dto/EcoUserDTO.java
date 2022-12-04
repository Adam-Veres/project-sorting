package com.epam.dto;

import com.epam.security.EcoUserRole;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

@Setter
@Getter
@ToString
public class EcoUserDTO {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\d]+$", message = "LoginError")
    @Size(min = 4, max = 24, message = "LoginSizeError")
    private String username;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\d]+$", message = "PasswordError")
    @Size(min = 9, max = 24, message = "PasswordSizeError")
    private String password;

    @NotBlank(message = "EmailEmptyError")
    @Email(regexp = "^[a-zA-Z\\d.]+@[a-zA-Z\\d.]+$", message = "EmailError")
    private String email;

    @JsonAlias("role")
    @JsonProperty("role")
    @NotNull(message = "RoleError")
    private EcoUserRole userRole;
}
