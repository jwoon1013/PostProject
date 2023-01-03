package com.hannunpalzi.postproject.controller;

import com.hannunpalzi.postproject.dto.*;
import com.hannunpalzi.postproject.entity.UserRoleEnum;
import com.hannunpalzi.postproject.security.UserDetailsImpl;
import com.hannunpalzi.postproject.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@Api(tags = {"댓글 CRUD api"})
public class CommentController {

    private final CommentService commentService;
    // 댓글 생성
    @PostMapping("/posts/{postId}/comments")
    @ApiImplicitParam(name = "postId", value = "게시글 id", dataTypeClass = Integer.class,example="1")
    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성한다.")
    public CommentCreatResponseDto createComment(@PathVariable Long postId , @RequestBody CommentRequestDto commentRequestDto , @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        return commentService.createComment(postId, commentRequestDto, username);
    }
    // 댓글 수정 (일반)
    @PutMapping("/posts/{postId}/comments/{commentId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "postId", value = "게시글 id", dataTypeClass = Integer.class,example="1"), @ApiImplicitParam(name = "commentId", value = "댓글 id", dataTypeClass = Integer.class,example="1")})
    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정한다.")
    public CommentUpdateResponseDto updateComment(@PathVariable Long commentId , @PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        return commentService.updateComment(commentId, postId, commentRequestDto, username);
    }
    //댓글 수정 (관리자)
    @Secured(UserRoleEnum.Authority.ADMIN)
    @PutMapping("/admin/posts/{postId}/comments/{commentId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "postId", value = "게시글 id", dataTypeClass = Integer.class,example="1"), @ApiImplicitParam(name = "commentId", value = "댓글 id", dataTypeClass = Integer.class,example="1")})
    @ApiOperation(value = "댓글 수정(관리자)", notes = "댓글을 관리자 권한으로 수정한다.")
    public CommentUpdateResponseDto updateCommentAdmin(@PathVariable Long commentId, @PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        return commentService.updateCommentAdmin(commentId, postId, commentRequestDto, username);
    }
    // 댓글 삭제 (일반)
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "postId", value = "게시글 id", dataTypeClass = Integer.class,example="1"), @ApiImplicitParam(name = "commentId", value = "댓글 id", dataTypeClass = Integer.class,example="1")})
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제한다.")
    public StatusResponseDto deleteComment(@PathVariable Long commentId, @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        return commentService.deleteComment(commentId, postId, username);
    }
    // 댓글 삭제 (관리자)
    @Secured(UserRoleEnum.Authority.ADMIN)
    @DeleteMapping("/admins/posts/{postId}/comments/{commentId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "postId", value = "게시글 id", dataTypeClass = Integer.class,example="1"), @ApiImplicitParam(name = "commentId", value = "댓글 id", dataTypeClass = Integer.class,example="1")})
    @ApiOperation(value = "댓글 삭제(관리자)", notes = "댓글을 관리자 권한으로 삭제한다.")
    public StatusResponseDto deleteCommentAdmin(@PathVariable Long commentId, @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        return commentService.deleteCommentAdmin(commentId, postId, username);
    }
}
