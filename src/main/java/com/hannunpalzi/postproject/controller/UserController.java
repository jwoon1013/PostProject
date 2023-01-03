package com.hannunpalzi.postproject.controller;

import com.hannunpalzi.postproject.dto.*;
import com.hannunpalzi.postproject.jwtUtil.JwtUtil;
import com.hannunpalzi.postproject.security.UserDetailsImpl;
import com.hannunpalzi.postproject.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
@Api(tags = {"회원가입 api"})
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @ApiOperation(value = "계정생성", notes = "계정생성을 한다.")
    @PostMapping("/users/signup")
    public ResponseEntity<StatusResponseDto> signup (@RequestBody @Valid UserSignupRequestDto requestDto) {
        StatusResponseDto responseDto = new StatusResponseDto(HttpStatus.CREATED.value(), "회원가입 완료");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        userService.signup(requestDto);
        return new ResponseEntity<>(responseDto, headers, HttpStatus.CREATED);
    }
    @ApiOperation(value = "관리자 계정 생성", notes = "관리자 계정을 생성한다.")
    @PostMapping("/admin/signup")
    public ResponseEntity<StatusResponseDto> signupAdmin (@RequestBody AdminSignupRequestDto requestDto) {
        StatusResponseDto responseDto = new StatusResponseDto(HttpStatus.CREATED.value(), "회원가입 완료");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        userService.signupAdmin(requestDto);
        return new ResponseEntity<>(responseDto, headers, HttpStatus.CREATED);
    }
    @ApiOperation(value = "로그인", notes = "로그인하고 헤더에 토큰을 반환한다.")
    @PostMapping("/users/login")
    public ResponseEntity<StatusResponseDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        StatusResponseDto statusResponseDto = new StatusResponseDto(HttpStatus.OK.value(), "로그인 완료");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        UsernameAndRoleResponseDto responseDto = userService.login(requestDto);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(responseDto.getUsername(), responseDto.getRole()));
        return new ResponseEntity<>(statusResponseDto, headers, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public void delete(@RequestBody UserDeleteRequestDto requestDto, @PathVariable Long userId){
        userService.delete(requestDto,userId);
    }
}

