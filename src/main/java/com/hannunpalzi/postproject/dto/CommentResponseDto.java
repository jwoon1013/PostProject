package com.hannunpalzi.postproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hannunpalzi.postproject.entity.Comment;
import com.hannunpalzi.postproject.entity.ReComment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponseDto {
    private long commentId;
    private String writer;
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;
    private Long like;
    private List<ReCommentResponseDto> reCommentResponseDtoList;

    public static CommentResponseDto valueOf(Comment comments) {
        return new CommentResponseDto(
                comments.getId(),
                comments.getWriter(),
                comments.getComment(),
                comments.getCreatedAt(),
                comments.getModifiedAt(),
                comments.getCommentLike(),
                comments.getReComments()
                );
    }

    public CommentResponseDto(long commentId, String writer, String comment, LocalDateTime createdAt, LocalDateTime modifiedAt, Long like, List<ReComment> reComments) {
        this.commentId = commentId;
        this.writer = writer;
        this.comment = comment;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.like = like;
        this.reCommentResponseDtoList = reComments.stream().map(ReCommentResponseDto::new).collect(Collectors.toList());
    }

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.writer = comment.getWriter();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.reCommentResponseDtoList = comment.getReComments().stream().map(ReCommentResponseDto::new).collect(Collectors.toList());
    }
}

