package com.hannunpalzi.postproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hannunpalzi.postproject.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateResponseDto {
    private Long categoryId;
    private String title;
    private String Contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;
    private String writer;

    public PostUpdateResponseDto(Post post){
        this.categoryId = post.getCategoryId();
        this.title  = post.getTitle();
        this.Contents = post.getContents();
        this.modifiedAt = post.getModifiedAt();
        this.writer = post.getWriter();
    }
}
