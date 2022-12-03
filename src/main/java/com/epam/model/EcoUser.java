package com.epam.model;

import com.epam.security.EcoUserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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