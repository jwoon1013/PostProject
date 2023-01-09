package com.hannunpalzi.postproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hannunpalzi.postproject.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreatedPostResponseDto {
    private final Long postId; // 게시글 id
    private final Long categoryId; // 카테고리 id
    private final String title; // 게시글 제목
    private final String contents; // 게시글 내용
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt; // 게시글 작성시간
    private final String writer; // 작성자명
    //createPost 리턴 DTO
    public CreatedPostResponseDto(Post post){
        this.postId = post.getId();
        this.categoryId = post.getCategoryId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.writer = post.getUser().getUsername();
    }

}
