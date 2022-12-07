package com.epam.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
///**** I would like to use Builder here */////
public class CommentMessage {

	@Id
	@GeneratedValue
	private long id;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Setter(value = AccessLevel.NONE)
	private LocalDateTime timeStamp;

	private boolean persistent;

	@Setter(value = AccessLevel.NONE)
	@ManyToOne
	private EcoUser creator;

	@Setter(value = AccessLevel.NONE)
	@ManyToOne
	private EcoService ecoService;

	@Setter(value = AccessLevel.NONE)
	private boolean updated;

	public void updateContent(String content) {
		this.content=content;
		timeStamp=LocalDateTime.now();
		updated=true;
	}
}
