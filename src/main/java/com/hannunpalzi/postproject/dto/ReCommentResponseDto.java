package com.hannunpalzi.postproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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
