package com.epam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.model.CommentMessage;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CommentMessageRepository extends JpaRepository<CommentMessage, Long>{
    Optional<CommentMessage> findByIdAndCreator_Username(Long aLong, String username);
    Optional<CommentMessage> findByIdAndEcoService_Owner_Username(Long aLong, String username);

    @Transactional
    int deleteAllByTimeStampBeforeAndPersistentIsFalse(LocalDateTime date);
}
