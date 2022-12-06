package com.epam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.model.CommentMessage;

public interface CommentMessageRepository extends JpaRepository<CommentMessage, Long>{

}
