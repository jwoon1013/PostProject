package com.hannunpalzi.postproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hannunpalzi.postproject.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateResponseDto {
    private long commentId;
    private String writer;
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public CommentUpdateResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.writer = comment.getWriter();
        this.comment = comment.getComment();
        this.modifiedAt = comment.getModifiedAt();

    }
}
