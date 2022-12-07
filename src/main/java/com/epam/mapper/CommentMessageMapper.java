package com.epam.mapper;

import com.epam.dto.EcoServiceDto;
import com.epam.model.EcoService;
import org.mapstruct.Mapper;

import com.epam.dto.CommentMessageDto;
import com.epam.model.CommentMessage;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMessageMapper {

	CommentMessage commentMessageDtoToCommentMessage(CommentMessageDto commentMessageDto);

	CommentMessageDto commentMessageToCommentMessageDto(CommentMessage commentMessage);

	List<CommentMessageDto> commentMessageListToCommentMessageListDto(List<CommentMessage> allCommentMessages);

}
