package com.epam.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentMessage {

	@Id
	@GeneratedValue
	private long id;
	@Column(columnDefinition = "TEXT")
	private String content;
	@Setter(value = AccessLevel.NONE)
	private LocalDateTime timeStamp;
	private boolean isPersistent;

	@Setter(value = AccessLevel.NONE)
	@ManyToOne
	private EcoUser creator;

	@Setter(value = AccessLevel.NONE)
	@ManyToOne
	private EcoService ecoService;

}
