package com.epam.model;

import com.epam.security.EcoUserRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Setter
@Getter
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class EcoUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true)
	private String username;

	@Column
	private String password;

	@Column(unique = true)
	private String email;

	@Column
	@Enumerated(EnumType.STRING)
	private EcoUserRole userRole;

	@Column
	private boolean locked=false;

	@JsonManagedReference
	@OneToMany (mappedBy="owner", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	private List<EcoService> services;

}