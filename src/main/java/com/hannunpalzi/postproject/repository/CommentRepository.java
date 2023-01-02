package com.hannunpalzi.postproject.repository;

import com.hannunpalzi.postproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByOrderByModifiedAtAsc();

    Optional<Comment> findById(Long id);
}
