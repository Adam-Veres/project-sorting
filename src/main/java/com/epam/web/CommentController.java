package com.epam.web;

import com.epam.dto.CommentMessageDto;
import com.epam.mapper.CommentMessageMapper;
import com.epam.security.Authority;
import com.epam.service.CommentService;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/ecoservice/comment")
public class CommentController {

  private final CommentService commentService;
  private final CommentMessageMapper commentMessageMapper;

  /**
   * Anyone can get all comments about ecoServiceId
   *
   * @param ecoServiceId
   * @return List<CommentMessageDto>
   */
  @GetMapping("/{ecoServiceId}")
  public List<CommentMessageDto> getAllMessagesForEcoService(
      @PathVariable final long ecoServiceId) {
    return commentMessageMapper.commentMessageListToCommentMessageListDto(
        commentService.getAllMessagesForEcoService(ecoServiceId));
  }

  @DeleteMapping("/{commentId}")
  public List<CommentMessageDto> removeCommentsFromEcoService(
          @PathVariable final long commentId) {
    return commentMessageMapper.commentMessageListToCommentMessageListDto(
            commentService.removeCommentsFromEcoService(commentId));
  }

  /**
   * User can add a new comment to a EcoService
   *
   * @param commentMessageDto
   * @param ecoServiceId
   * @return CommentMessageDto
   */
  @PreAuthorize(Authority.HAS_USER_AUTHORITY)
  @PostMapping("/{ecoServiceId}")
  public CommentMessageDto addNewCommentToEcoService(
      @RequestBody @Valid final CommentMessageDto commentMessageDto,
      @PathVariable final long ecoServiceId) {
    return commentMessageMapper.commentMessageToCommentMessageDto(
        commentService.addNewCommentToEcoService(
            commentMessageMapper.commentMessageDtoToCommentMessage(commentMessageDto),
            ecoServiceId));
  }

  /**
   * Service Provider user (owner) has authority to change persistence of a comment.
   *
   * @param commentId id of comment what you want to change
   * @param isPersistence new value of field. store persistence = true
   * @return CommentMessageDto
   */
  @PreAuthorize(Authority.HAS_SERVICE_AUTHORITY)
  @PutMapping(path = "/{commentId}", params = "is_persistence")
  public CommentMessageDto changeCommentPersistence(
      @PathVariable final long commentId,
      @RequestParam(name = "is_persistence") final boolean isPersistence) {
    return commentMessageMapper.commentMessageToCommentMessageDto(
        commentService.changeCommentPersistence(commentId, isPersistence));
  }

  /**
   * Modify existing comment by owner
   *
   * @param commentMessageDto
   * @return modified comment in CommentDto
   */
  @PutMapping
  @PreAuthorize(Authority.HAS_USER_AUTHORITY)
  public CommentMessageDto updateCommentMessage(
      @RequestBody @Valid final CommentMessageDto commentMessageDto) {
    return commentMessageMapper.commentMessageToCommentMessageDto(
        commentService.updateTextForExistedComment(commentMessageDto));
  }
}
