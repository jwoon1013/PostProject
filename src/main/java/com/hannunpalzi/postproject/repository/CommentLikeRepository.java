package com.hannunpalzi.postproject.repository;

import com.hannunpalzi.postproject.entity.Comment;
import com.hannunpalzi.postproject.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {
    Optional<CommentLike> findByUsername(String username);
    void deleteByUsername(String username);
    void deleteAllByComment(Comment comment);
    void deleteAllByPostId(Long postId);
}