package com.hannunpalzi.postproject.repository;

import com.hannunpalzi.postproject.entity.ReCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReCommentLikeRepository extends JpaRepository<ReCommentLike, Long> {
    Optional<ReCommentLike> findByUsername(String username);

    void deleteByUsername(String username);
}
