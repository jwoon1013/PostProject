package com.hannunpalzi.postproject.responseMessage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ResponseMessages {
    LIKE_SUCCESS(200,"좋아요"),
    LIKE_CANCEL(200,"좋아요가 취소되었습니다");

    private final int statusCode;
    private final String message;
}
