package com.epam.model;

import com.epam.security.EcoUserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

	@JsonManagedReference
	@OneToMany(mappedBy = "creator")
	private List<CommentMessage> comments;

	@JsonIgnore
	@OneToMany(mappedBy = "creator", fetch=FetchType.LAZY)
	private List<ServiceRating> ratings;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EcoUser ecoUser = (EcoUser) o;
		return id == ecoUser.id;
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}
}