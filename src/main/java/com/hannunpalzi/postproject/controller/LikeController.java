package com.hannunpalzi.postproject.controller;

import com.hannunpalzi.postproject.dto.LikeRequestDto;
import com.hannunpalzi.postproject.dto.StatusResponseDto;
import com.hannunpalzi.postproject.jwtUtil.JwtUtil;
import com.hannunpalzi.postproject.service.LikeService;
import com.hannunpalzi.postproject.service.TokenChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final JwtUtil jwtUtil;
    private final TokenChecker tokenChecker;

    @PostMapping("/post/{id}/like")
    public ResponseEntity<StatusResponseDto> postLike(@PathVariable Long id, @RequestBody LikeRequestDto likeRequestDto, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        String username = tokenChecker.checkToken(token);
        return likeService.postLike(username,id,likeRequestDto);
    }
    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<StatusResponseDto> commentLike(@PathVariable Long commentId, @RequestBody LikeRequestDto likeRequestDto, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        String username = tokenChecker.checkToken(token);
        return likeService.commentLike(username,commentId,likeRequestDto);
    }
}