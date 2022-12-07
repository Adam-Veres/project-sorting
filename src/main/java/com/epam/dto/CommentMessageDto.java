package com.epam.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentMessageDto {

	private long id;
	@NotBlank
	private String content;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timeStamp;
	private boolean isPersistent;
	private boolean updated;
	
}
