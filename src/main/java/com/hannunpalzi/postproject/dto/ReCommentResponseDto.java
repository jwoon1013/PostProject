package com.hannunpalzi.postproject.dto;

import com.hannunpalzi.postproject.entity.ReComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReCommentResponseDto {
    private long reCommentId;
    private String writer;
    private String reComment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long like;

    public ReCommentResponseDto(ReComment reComment){
        this.reCommentId = reComment.getId();
        this.writer = reComment.getWriter();
        this.reComment = reComment.getReComment();
        this.createdAt = reComment.getCreatedAt();
        this.modifiedAt = reComment.getModifiedAt();
        this.like = (long)reComment.getLike().size();
    }



}
