package com.hannunpalzi.postproject.dto;

import com.hannunpalzi.postproject.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateResponseDto {
    private String title;
    private String Contents;
    private LocalDateTime modifiedAt;
    private String writer;

    public PostUpdateResponseDto(Post post){
        this.title  = post.getTitle();
        this.Contents = post.getContents();
        this.modifiedAt = post.getModifiedAt();
        this.writer = post.getWriter();
    }
}
