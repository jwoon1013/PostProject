package com.hannunpalzi.postproject.exception;

import com.hannunpalzi.postproject.dto.StatusResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Objects;
@Service
public class ExceptionService {
    public StatusResponseDto getErrorResponse(Exception exception) {
        return StatusResponseDto.ExceptionValueOf(exception);
    }

//    public StatusResponseDto makeMethodArgumentNotValidException(BindingResult bindingResult) {
//        //에러가 있다면
//        if (bindingResult.hasErrors()) {
//            //DTO에 유효성체크를 걸어놓은 어노테이션명을 가져온다.
//            String bindResultCode = Objects.requireNonNull(bindingResult.getFieldError()).getCode();
//            //유형지정
//            switch (Objects.requireNonNull(bindResultCode)) {
//                case "Pattern" -> {
//                    return StatusResponseDto.ExceptionValueOf(Exception.INVALID_PATTERN);
//                }
//                case "Size" -> {
//                    return StatusResponseDto.ExceptionValueOf(Exception.NONE);
//                }
//            }
//        }
//        return StatusResponseDto.ExceptionValueOf(Exception.NONE);
//    }
}
