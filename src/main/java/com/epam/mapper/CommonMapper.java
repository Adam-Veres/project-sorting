package com.epam.mapper;

import com.epam.dto.CommentMessageDto;
import com.epam.model.CommentMessage;
import com.epam.repository.ServiceRatingRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonMapper {

  private final PasswordEncoder passwordEncoder;

  private final ServiceRatingRepository serviceRatingRepository;

  private final CommentMessageMapper commentMessageMapper;

  @PasswordEncoderMapping
  public String encode(final String value) {
    return passwordEncoder.encode(value);
  }

  @RatingEncoderMapping
  public Float getRating(final long serviceId) {
    return serviceRatingRepository.averageServiceRating(serviceId).orElse(0.0F);
  }

  @CommentsListMapping
  public List<CommentMessageDto> commentMessageSetToCommentMessageDtoList(
      final Set<CommentMessage> set) {
    if (set == null) {
      return null;
    }

    final List<CommentMessageDto> list = new ArrayList<>(set.size());
    for (CommentMessage commentMessage : set) {
      list.add(commentMessageMapper.commentMessageToCommentMessageDto(commentMessage));
    }
    list.sort((c1, c2) -> c1.getId() < c2.getId() ? -1 : 1);
    return list;
  }
}
