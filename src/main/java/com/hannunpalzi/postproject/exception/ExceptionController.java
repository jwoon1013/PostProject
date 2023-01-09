package com.hannunpalzi.postproject.exception;

import com.hannunpalzi.postproject.dto.StatusResponseDto;
import com.hannunpalzi.postproject.exception.illegalArgumentExceptionCustom.InvalidTokenException;
import com.hannunpalzi.postproject.exception.illegalArgumentExceptionCustom.InvalidUsernameException;
import com.hannunpalzi.postproject.exception.illegalArgumentExceptionCustom.InvalidWriterException;
import com.hannunpalzi.postproject.exception.illegalArgumentExceptionCustom.NotFoundUserException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

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

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<StatusResponseDto> AccessDeniedExceptionHandler(AccessDeniedException e){
        StatusResponseDto statusResponseDto = new StatusResponseDto(HttpStatus.FORBIDDEN.value(),e.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(JwtException.class)
    private ResponseEntity<StatusResponseDto> JwtExceptionHandler(JwtException e){
        StatusResponseDto statusResponseDto = new StatusResponseDto(HttpStatus.UNAUTHORIZED.value(),e.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SecurityException.class)
    private ResponseEntity<StatusResponseDto> SecurityExceptionHandler(SecurityException e){
        StatusResponseDto statusResponseDto = new StatusResponseDto(HttpStatus.UNAUTHORIZED.value(),e.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MalformedJwtException.class)
    private ResponseEntity<StatusResponseDto> MalformedJwtExceptionHandler(MalformedJwtException e){
        StatusResponseDto statusResponseDto = new StatusResponseDto(HttpStatus.UNAUTHORIZED.value(),e.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    private ResponseEntity<StatusResponseDto> SignatureExceptionHandler(SignatureException e){
        StatusResponseDto statusResponseDto = new StatusResponseDto(HttpStatus.UNAUTHORIZED.value(),e.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<StatusResponseDto> ConstraintViolationExceptionHandler(ConstraintViolationException e){
        String message = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList()
                .get(0);
        StatusResponseDto statusResponseDto = new StatusResponseDto(HttpStatus.BAD_REQUEST.value(), message);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.BAD_REQUEST);
    }
    ///////////////////////////////////////////커스텀익셉션/////////////////////////////////////////////////////////////
    @ExceptionHandler(InvalidTokenException.class)
    private ResponseEntity<StatusResponseDto> invalidTokenException(InvalidTokenException e){
        log.info(e.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        StatusResponseDto statusResponseDto = exceptionService.getErrorResponse(Exception.INVALID_TOKEN);
        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidUsernameException.class)
    private ResponseEntity<StatusResponseDto> invalidUsernameException(InvalidUsernameException e){
        log.info(e.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        StatusResponseDto statusResponseDto = exceptionService.getErrorResponse(Exception.INVALID_USERNAME);
        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidWriterException.class)
    private ResponseEntity<StatusResponseDto> invalidWriterException(InvalidWriterException e){
        log.info(e.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        StatusResponseDto statusResponseDto = exceptionService.getErrorResponse(Exception.INVALID_WRITER);
        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundUserException.class)
    private ResponseEntity<StatusResponseDto> invalidWriterException(NotFoundUserException e){
        log.info(e.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        StatusResponseDto statusResponseDto = exceptionService.getErrorResponse(Exception.NOT_FOUND_USER);
        return new ResponseEntity<>(statusResponseDto,httpHeaders,HttpStatus.BAD_REQUEST);
    }


}
