package com.epam.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

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
