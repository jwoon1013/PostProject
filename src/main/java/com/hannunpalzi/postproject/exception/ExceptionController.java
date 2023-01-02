package com.hannunpalzi.postproject.exception;

import com.hannunpalzi.postproject.dto.StatusResponseDto;
import com.hannunpalzi.postproject.exception.illegalArgumentExceptionCustom.InvalidTokenException;
import com.hannunpalzi.postproject.exception.illegalArgumentExceptionCustom.InvalidUsernameException;
import com.hannunpalzi.postproject.exception.illegalArgumentExceptionCustom.InvalidWriterException;
import com.hannunpalzi.postproject.exception.illegalArgumentExceptionCustom.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.charset.Charset;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    private final ExceptionService exceptionService;
    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<StatusResponseDto> illegalArgumentExceptionHandler(IllegalArgumentException e){
        StatusResponseDto statusResponseDto = new StatusResponseDto(HttpStatus.BAD_REQUEST.value(),e.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StatusResponseDto> methodValidException(MethodArgumentNotValidException e){
        StatusResponseDto statusResponseDto = new StatusResponseDto(HttpStatus.BAD_REQUEST.value(),e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.BAD_REQUEST);
    }
    ///////////////////////////////////////////커스텀익셉션/////////////////////////////////////////////////////////////
//    @ExceptionHandler(InvalidTokenException.class)
//    private ResponseEntity<StatusResponseDto> invalidTokenException(InvalidTokenException e){
//        log.info(e.getMessage());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
//        StatusResponseDto statusResponseDto = exceptionService.getErrorResponse(Exception.INVALID_TOKEN);
//        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler(InvalidUsernameException.class)
//    private ResponseEntity<StatusResponseDto> invalidUsernameException(InvalidUsernameException e){
//        log.info(e.getMessage());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
//        StatusResponseDto statusResponseDto = exceptionService.getErrorResponse(Exception.INVALID_USERNAME);
//        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler(InvalidWriterException.class)
//    private ResponseEntity<StatusResponseDto> invalidWriterException(InvalidWriterException e){
//        log.info(e.getMessage());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
//        StatusResponseDto statusResponseDto = exceptionService.getErrorResponse(Exception.INVALID_WRITER);
//        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler(InvalidWriterException.class)
//    private ResponseEntity<StatusResponseDto> notFoundUserException(NotFoundUserException e){
//        log.info(e.getMessage());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
//        StatusResponseDto statusResponseDto = exceptionService.getErrorResponse(Exception.NOT_FOUND_USER);
//        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.BAD_REQUEST);
//    }


}
