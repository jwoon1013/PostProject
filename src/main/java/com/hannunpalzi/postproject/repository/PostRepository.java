package com.hannunpalzi.postproject.repository;

import com.hannunpalzi.postproject.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc(Pageable pageable); // 전체 게시글 목록 리스트
    List<Post> findByCategoryIdOrderByModifiedAtDesc(Long categoryId);
}
