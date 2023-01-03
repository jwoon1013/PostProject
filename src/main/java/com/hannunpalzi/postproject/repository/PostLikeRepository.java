package com.hannunpalzi.postproject.repository;

import com.hannunpalzi.postproject.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUsername(String username);
    void deleteByUsername(String username);
}
