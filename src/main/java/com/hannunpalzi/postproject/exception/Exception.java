package com.hannunpalzi.postproject.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Exception {
    NONE(400, "지정해주지 않은 유형의 MethodArgumentNotValidException 입니다."),
    INVALID_TOKEN(400, "토큰이 유효하지 않습니다."),
    INVALID_WRITER(400, "작성자만 수정/삭제할 수 있습니다. 관리자라면 관리자 url을 이용해 주십시오."),
    INVALID_USERNAME(400, "중복된 username입니다"),
    NOT_FOUND_USER(400, "회원을 찾을 수 없습니다"),
    INVALID_PATTERN(400, "username은 최소 4자, 최대 10자 영어소문자와 숫자만 입력할 수 있고, password는 최소 8자, 최대15자 영어 대소문자, 특수문자를 입력할 수 있습니다.");

    private final int statusCode;
    private final String message;
}
