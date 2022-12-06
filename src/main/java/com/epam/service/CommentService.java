package com.epam.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.epam.model.CommentMessage;
import com.epam.model.EcoService;
import com.epam.model.EcoUser;
import com.epam.repository.CommentMessageRepository;
import com.epam.repository.EcoServiceRepository;
import com.epam.repository.EcoUserRepository;

@Service
public class CommentService {

	@Autowired
	CommentMessageRepository commentMessageRepository;

	@Autowired
	EcoUserRepository ecoUserRepository;
	
	@Autowired
	EcoServiceRepository ecoServiceREpository;

	/**
	 * User can add a new comment to a EcoService
	 * @param commentMessageDto
	 * @param ecoServiceId
	 * @return CommentMessageDto
	 */
	@Transactional
	public CommentMessage addNewCommentToEcoService(final CommentMessage commentMessage, final long ecoServiceId) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		EcoUser actualEcoUser = findEcoUser(authentication);
		EcoService givenEcoService = findEcoService(ecoServiceId);
		return commentMessageRepository.save(new CommentMessage(0, commentMessage.getContent(), LocalDateTime.now(), false, actualEcoUser, givenEcoService));
	}

	private EcoService findEcoService(final long ecoServiceId) {
		Optional<EcoService> ecoservice = ecoServiceREpository.findById(ecoServiceId);
		EcoService givenEcoService = ecoservice.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Eco Service does not exist eith this id!"));
		return givenEcoService;
	}

	private EcoUser findEcoUser(final Authentication authentication) {
		Optional<EcoUser> optionalEcoUser = ecoUserRepository.findByUsername(authentication.getName());
		EcoUser actualEcoUser = optionalEcoUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Eco User does not exist!"));
		return actualEcoUser;
	}

	/**
	 * Service Provider user (owner) has authority to change persistence of a comment.
	 * @param commentId id of comment what you want to change
	 * @param isPersistence new value of field. store persistence = true
	 * @return CommentMessageDto
	 */
	@Transactional
	public CommentMessage changeCommentPersistancy(final long commentId, final boolean isPersistence) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		EcoUser actualEcoUser = findEcoUser(authentication);
		CommentMessage actualMessage = findCommit(commentId);
		if(actualEcoUser.getId() == actualMessage.getEcoService().getOwner().getId()) {
			actualMessage.setPersistent(isPersistence);
			return commentMessageRepository.save(actualMessage);
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment does not belongs to you!");
	}

	private CommentMessage findCommit(final long commentId) {
		if(commentMessageRepository.existsById(commentId)) {
			return commentMessageRepository.findById(commentId).get();
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment does not exist with this id!");
	}
	
}
