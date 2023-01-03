package com.hannunpalzi.postproject.dto;

import com.hannunpalzi.postproject.entity.Comment;
import com.hannunpalzi.postproject.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    private String comment;



    public Comment toEntity(User user){
        return new Comment(this.comment, user);
    }
}
