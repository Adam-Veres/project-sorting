package com.epam.service;

import com.epam.dto.CommentMessageDto;
import com.epam.model.CommentMessage;
import com.epam.model.EcoService;
import com.epam.model.EcoUser;
import com.epam.repository.CommentMessageRepository;
import com.epam.repository.EcoServiceRepository;
import com.epam.repository.EcoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentMessageRepository commentMessageRepository;

  private final EcoUserRepository ecoUserRepository;

  private final EcoServiceRepository ecoServiceRepository;

  /**
   * User can add a new comment to a EcoService
   *
   * @param commentMessage
   * @param ecoServiceId
   * @return CommentMessageDto
   */
  @Transactional
  public CommentMessage addNewCommentToEcoService( final CommentMessage commentMessage, final long ecoServiceId) {
    final EcoService ecoservice = ecoServiceRepository
    		.findById(ecoServiceId)
    		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Eco Service does not exist with this id!"));
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    EcoUser actualEcoUser = ecoUserRepository.findByUsername(authentication.getName()).get();
    boolean persistence = (ecoservice.getOwner().equals(actualEcoUser)) && commentMessage.isPersistent();
    return commentMessageRepository.save(
        new CommentMessage(
            0,
            commentMessage.getContent(),
            LocalDateTime.now(),
            persistence,
            actualEcoUser,
            ecoservice,
            false));
  }

  /**
   * Service Provider user (owner) has authority to change persistence of a comment.
   *
   * @param commentId id of comment what you want to change
   * @param isPersistence new value of field. store persistence = true
   * @return CommentMessageDto
   */
  @Transactional
  public CommentMessage changeCommentPersistence( final long commentId, final boolean isPersistence) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    final CommentMessage actualMessage = commentMessageRepository
            .findByIdAndEcoService_Owner_Username(commentId, authentication.getName())
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment does not exist with this id!"));
    actualMessage.setPersistent(isPersistence);
    return commentMessageRepository.save(actualMessage);
  }

  /**
   * Modify content of the comment. Creation date also updated!
   * @param commentMessageDto
   * @return modified comment in Comment object
   */
  @Transactional
  public CommentMessage updateTextForExistedComment(CommentMessageDto commentMessageDto) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    final CommentMessage actualMessage = commentMessageRepository
            .findByIdAndCreator_Username(commentMessageDto.getId(), authentication.getName())
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment does not exist with this id!"));
    actualMessage.updateContent(commentMessageDto.getContent());
    return commentMessageRepository.save(actualMessage);
  }
}
