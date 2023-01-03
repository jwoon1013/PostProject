package com.hannunpalzi.postproject.controller;

import com.hannunpalzi.postproject.dto.CommentRequestDto;
import com.hannunpalzi.postproject.dto.ReCommentRequestDto;
import com.hannunpalzi.postproject.dto.ReCommentResponseDto;
import com.hannunpalzi.postproject.dto.StatusResponseDto;
import com.hannunpalzi.postproject.entity.ReComment;
import com.hannunpalzi.postproject.entity.UserRoleEnum;
import com.hannunpalzi.postproject.security.UserDetailsImpl;
import com.hannunpalzi.postproject.service.ReCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
public class ReCommentController {
    private final ReCommentService reCommentService;

    //대댓글 작성
    @PostMapping("/posts/{postId}/comments/{commentId}/re-comments")
    public ReCommentResponseDto createReComment(@PathVariable Long commentId, @PathVariable Long postId, @RequestBody ReCommentRequestDto reCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        return reCommentService.createReComment(commentId, postId, reCommentRequestDto, username);
    }

    //대댓글 수정 (일반유저)
    @PutMapping("/posts/{postId}/comments/{commentId}/re-comments/{reCommentId}")
    public ReCommentResponseDto updateReComment(@PathVariable Long reCommentId, @PathVariable Long postId, @PathVariable Long commentId, @RequestBody ReCommentRequestDto recommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        return reCommentService.updateReComment(reCommentId, commentId,  postId, recommentRequestDto, username);
    }
    //대댓글 수정 (관리자)
    @Secured(UserRoleEnum.Authority.ADMIN)
    @PutMapping("/admin/posts/{postId}/comments/{commentId}/re-comments/{reCommentId}")
    public ReCommentResponseDto updateReCommentAdmin(@PathVariable Long reCommentId, @PathVariable Long postId, @PathVariable Long commentId, @RequestBody ReCommentRequestDto recommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        return reCommentService.updateReCommentAdmin(reCommentId, commentId, postId, recommentRequestDto, username);
    }
    //대댓글 삭제 (일반유저)
    @DeleteMapping("/posts/{postId}/comments/{commentId}/re-comments/{reCommentId}")
    public ResponseEntity<StatusResponseDto> deleteReComment(@PathVariable Long reCommentId, @PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        StatusResponseDto statusResponseDto = reCommentService.deleteReComment(reCommentId, commentId, postId, username);
        return new ResponseEntity<>(statusResponseDto, headers, HttpStatus.OK);
    }

    //대댓글 삭제 (관리자)
    @Secured(UserRoleEnum.Authority.ADMIN)
    @DeleteMapping("/admin/posts/{postId}/comments/{commentId}/re-comments/{reCommentId}")
    public ResponseEntity<StatusResponseDto> deleteReCommentAdmin(@PathVariable Long reCommentId, @PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        StatusResponseDto statusResponseDto = reCommentService.deleteReComment(reCommentId, commentId, postId, username);
        return new ResponseEntity<>(statusResponseDto, headers, HttpStatus.OK);
    }


}
