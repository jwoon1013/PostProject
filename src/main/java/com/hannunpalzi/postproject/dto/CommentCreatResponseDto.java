package com.hannunpalzi.postproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hannunpalzi.postproject.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentCreatResponseDto {

    private final Long commentId;
    private final String comment;
    private final String writer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;


    public CommentCreatResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.comment = comment.getComment();
        this.writer = comment.getWriter();
        this.createdAt = comment.getCreatedAt();
    }
}
