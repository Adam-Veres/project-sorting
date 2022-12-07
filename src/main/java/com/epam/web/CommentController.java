package com.epam.web;

import javax.validation.Valid;

import com.epam.dto.EcoServiceDto;
import com.epam.dto.JwtControllersResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.CommentMessageDto;
import com.epam.mapper.CommentMessageMapper;
import com.epam.security.Authority;
import com.epam.service.CommentService;

import lombok.AllArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/ecoservice/comment")
public class CommentController {

	private final CommentService commentService;
	private final CommentMessageMapper commentMessageMapper;

	/**
	 * User can add a new comment to a EcoService
	 * @param commentMessageDto
	 * @param ecoServiceId
	 * @return CommentMessageDto
	 */
	@PreAuthorize(Authority.HAS_USER_AUTHORITY)
	@PostMapping("/{ecoServiceId}")
	public CommentMessageDto addNewCommentToEcoService(@RequestBody @Valid final CommentMessageDto commentMessageDto, @PathVariable final long ecoServiceId) {
		return commentMessageMapper.commentMessageToCommentMessageDto(
				commentService.addNewCommentToEcoService(
						commentMessageMapper.commentMessageDtoToCommentMessage(commentMessageDto), ecoServiceId));
	}

	/**
	 * Service Provider user (owner) has authority to change persistence of a comment.
	 * @param commentId id of comment what you want to change
	 * @param isPersistence new value of field. store persistence = true
	 * @return CommentMessageDto
	 */
	@PreAuthorize(Authority.HAS_SERVICE_AUTHORITY)
	@PutMapping(path = "/{commentId}", params = "is_persistence")
	public CommentMessageDto changeCommentPersistence(@PathVariable final long commentId, @RequestParam(name = "is_persistence") final boolean isPersistence) {
		return commentMessageMapper.commentMessageToCommentMessageDto(
				commentService.changeCommentPersistence(commentId, isPersistence));
	}

	@PutMapping
	@PreAuthorize(Authority.HAS_USER_AUTHORITY)
	public CommentMessageDto updateCommentMessage(@RequestBody @Valid final CommentMessageDto commentMessageDto) {
		return commentMessageMapper.commentMessageToCommentMessageDto(
				commentService.updateTextForExistedComment(commentMessageDto));

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleAuthenticationExceptionException(final Exception e) {
		return new ResponseEntity<>(new CommentMessageDto(), HttpStatus.NO_CONTENT);
	}
}
