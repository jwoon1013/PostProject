package com.hannunpalzi.postproject.responseMessage;

import com.hannunpalzi.postproject.dto.StatusResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseMessageService {
    public ResponseEntity<StatusResponseDto> likeOk(){
        StatusResponseDto statusResponseDto = StatusResponseDto.valueOf(ResponseMessages.LIKE_SUCCESS);
        return new ResponseEntity<>(statusResponseDto, HttpStatus.OK);
    }
    public ResponseEntity<StatusResponseDto> likeCancel(){
        StatusResponseDto statusResponseDto = StatusResponseDto.valueOf(ResponseMessages.LIKE_CANCEL);
        return new ResponseEntity<>(statusResponseDto, HttpStatus.OK);
    }
}
