package com.hannunpalzi.postproject.dto;

import com.hannunpalzi.postproject.exception.Exception;
import com.hannunpalzi.postproject.responseMessage.ResponseMessages;
import lombok.Getter;

@Getter
public class StatusResponseDto {
    private final int statusCode;
    private final String message;

    public StatusResponseDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    public static StatusResponseDto valueOf(ResponseMessages responseMessages) {
        return new StatusResponseDto(responseMessages.getStatusCode(), responseMessages.getMessage());
    }

    public static StatusResponseDto ExceptionValueOf(Exception exception) {
        return new StatusResponseDto(exception.getStatusCode(), exception.getMessage());
    }

}
