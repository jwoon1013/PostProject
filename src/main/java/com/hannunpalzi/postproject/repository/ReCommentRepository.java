package com.hannunpalzi.postproject.repository;

import com.hannunpalzi.postproject.entity.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReCommentRepository extends JpaRepository<ReComment, Long> {
    List<ReComment> findAllByOrderByModifiedAtAsc();
}
