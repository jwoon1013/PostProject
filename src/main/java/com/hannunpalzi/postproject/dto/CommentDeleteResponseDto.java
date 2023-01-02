package com.hannunpalzi.postproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDeleteResponseDto {

    private int statusCode;
    private String statusMessage;

    public CommentDeleteResponseDto(String statusMessage){
        this.statusCode = 200;
        this.statusMessage = statusMessage;
    }
}
