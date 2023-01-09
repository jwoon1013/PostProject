package com.hannunpalzi.postproject.dto;

import com.hannunpalzi.postproject.entity.Post;
import com.hannunpalzi.postproject.entity.User;
import lombok.Getter;

@Getter
public class PostRequestDto {
    private Long categoryId; // 카테고리 id
    private String title; // 게시글 제목
    private String contents; // 게시글 내용

    // PostRequestDto 로 Post 생성
    public Post toEntity(User user){
        return new Post(this.title, this.contents, user, this.categoryId);
    }

}
