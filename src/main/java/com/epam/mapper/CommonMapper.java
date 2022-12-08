package com.epam.mapper;

import com.epam.dto.CommentMessageDto;
import com.epam.model.CommentMessage;
import com.epam.repository.ServiceRatingRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonMapper {

    private final PasswordEncoder passwordEncoder;

    private final ServiceRatingRepository serviceRatingRepository;
    
    private final CommentMessageMapper commentMessageMapper;

    @PasswordEncoderMapping
    public String encode(String value) {
        return passwordEncoder.encode(value);
    }

    @RatingEncoderMapping
    public Float getRating(long serviceId){
        return serviceRatingRepository.averageServiceRating(serviceId).orElse(0.0F);
    }
    
    @CommentsListMapping
    public List<CommentMessageDto> commentMessageSetToCommentMessageDtoList(Set<CommentMessage> set) {
        if ( set == null ) {
            return null;
        }

        List<CommentMessageDto> list = new ArrayList<CommentMessageDto>( set.size() );
        for ( CommentMessage commentMessage : set ) {
            list.add( commentMessageMapper.commentMessageToCommentMessageDto( commentMessage ) );
        }
        list.sort((c1, c2) -> c1.getId() < c2.getId() ? -1 : 1);
        return list;
    }

}
