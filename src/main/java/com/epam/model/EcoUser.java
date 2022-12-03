package com.epam.model;

import com.epam.security.EcoUserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table
@Setter
@Getter
public class EcoUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true)
	private String username;

	@Column
	private String password;

	@Column
	private String email;

	@Column
	@Enumerated(EnumType.STRING)
	private EcoUserRole userRole;

	@Column
	private boolean locked=false;


}