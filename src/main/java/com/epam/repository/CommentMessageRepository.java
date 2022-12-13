package com.epam.repository;

import com.epam.model.CommentMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CommentMessageRepository extends JpaRepository<CommentMessage, Long> {

  Optional<CommentMessage> findByIdAndCreator_Username(final Long aLong, final String username);

  Optional<CommentMessage> findByIdAndEcoService_Owner_Username(
      final Long aLong, final String username);

  @Transactional
  int deleteAllByTimeStampBeforeAndPersistentIsFalse(final LocalDateTime date);

  List<CommentMessage> findAllByEcoService_Id(final long id);
}
