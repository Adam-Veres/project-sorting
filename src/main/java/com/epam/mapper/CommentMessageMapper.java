package com.epam.mapper;

import com.epam.dto.CommentMessageDto;
import com.epam.model.CommentMessage;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMessageMapper {

  CommentMessage commentMessageDtoToCommentMessage(final CommentMessageDto commentMessageDto);

  CommentMessageDto commentMessageToCommentMessageDto(final CommentMessage commentMessage);

  List<CommentMessageDto> commentMessageListToCommentMessageListDto(
      final List<CommentMessage> allCommentMessages);
}
