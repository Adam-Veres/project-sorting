package com.epam.service;

import com.epam.repository.CommentMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

@Component
@RequiredArgsConstructor
public class OldCommentsRemover {

  @Value("${project.old-comments-remove-minutes}")
  private Integer waitInterval;

  private final CommentMessageRepository commentMessageRepository;

  public void run(){
      new Timer().scheduleAtFixedRate(new TimerTask() {
          @Override
          public void run() {
              commentMessageRepository.deleteAllByTimeStampBeforeAndPersistentIsFalse(LocalDateTime.now().minusWeeks(2));
          }
      }, 0, waitInterval*60*1000);
  }
}
