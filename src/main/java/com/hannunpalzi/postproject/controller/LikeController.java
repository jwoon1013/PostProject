package com.hannunpalzi.postproject.controller;

import com.hannunpalzi.postproject.dto.LikeRequestDto;
import com.hannunpalzi.postproject.dto.StatusResponseDto;
import com.hannunpalzi.postproject.security.UserDetailsImpl;
import com.hannunpalzi.postproject.service.LikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
@Api(tags = {"좋아요 api"})
public class LikeController {
    private final LikeService likeService;
    @ApiImplicitParam(name = "postId", value = "게시글 id", dataTypeClass = Integer.class,example="1")
    @ApiOperation(value = "게시글 좋아요", notes = "게시글에 좋아요를 한다.")
    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<StatusResponseDto> postLike(@PathVariable Long postId, @RequestBody LikeRequestDto likeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        return likeService.postLike(username,postId,likeRequestDto);
    }
    @ApiImplicitParams({
    @ApiImplicitParam(name = "postId", value = "게시글 id", dataTypeClass = Integer.class, example = "1"),
    @ApiImplicitParam(name = "commentId", value = "댓글 id", dataTypeClass = Integer.class, example = "1")
    })
    @ApiOperation(value = "댓글 좋아요", notes = "댓글에 좋아요를 한다.")
    @PostMapping("/posts/{postId}/comments/{commentId}/like")
    public ResponseEntity<StatusResponseDto> commentLike(@PathVariable Long commentId, @PathVariable Long postId, @RequestBody LikeRequestDto likeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        return likeService.commentLike(username,postId,commentId,likeRequestDto);
    }

    @ApiImplicitParam(name = "reCommentId", value = "대댓글 id", dataTypeClass = Integer.class,example="1")
    @ApiOperation(value = "대댓글 좋아요", notes = "대댓글에 좋아요를 한다.")
    @PostMapping("/re-comments/{reCommentId}/like")
    public ResponseEntity<StatusResponseDto> reCommentLike(@PathVariable Long reCommentId, @RequestBody LikeRequestDto likeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        return likeService.reCommentLike(username, reCommentId, likeRequestDto);
    }
}