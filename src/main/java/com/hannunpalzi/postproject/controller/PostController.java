package com.hannunpalzi.postproject.controller;

import com.hannunpalzi.postproject.dto.*;
import com.hannunpalzi.postproject.entity.UserRoleEnum;
import com.hannunpalzi.postproject.security.UserDetailsImpl;
import com.hannunpalzi.postproject.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Api(tags = {"게시글 작성 CRUD api"})
public class PostController {
    private final PostService postService;
//    private final JwtUtil jwtUtil; // userDetails 사용으로 필요없어짐
//    private final TokenChecker tokenChecker;
    //게시글 작성

    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성.")
    @PostMapping("/posts")
    public CreatedPostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        return postService.createPost(postRequestDto, username);
    }


    @ApiImplicitParam(name = "postId", value = "게시글 id", dataTypeClass = Integer.class,example="1")
    @ApiOperation(value = "게시글 조회", notes = "선택한 게시글을 조회한다.")
    @GetMapping("/posts/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) { return postService.getPost(postId);}

    // 전체 게시글 조회

    @ApiOperation(value = "게시글 전체 조회", notes = "게시글을 전부 조회한다.")
    @GetMapping("/posts")
    public List<PostResponseDto> getTotalPostsList() { return postService.getTotalPostsList();}

    //게시글 수정 (일반유저)
    @ApiImplicitParam(name = "postId", value = "게시글 id", dataTypeClass = Integer.class,example="1")
    @ApiOperation(value = "게시글 수정", notes = "선택한 게시글을 수정한다.")
    @PutMapping("/posts/{postId}")
    public PostUpdateResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        return postService.updatePost(postId, postRequestDto, username);
    }

    //게시글 수정 (관리자)
    @ApiImplicitParam(name = "postId", value = "게시글 id", dataTypeClass = Integer.class,example="1")
    @ApiOperation(value = "게시글 수정(관리자)", notes = "선택한 게시글을 관리자 권한으로 수정한다.")
    @Secured(UserRoleEnum.Authority.ADMIN)
    @PutMapping("/admin/posts/{postId}")
    public PostUpdateResponseDto updatePostAdmin(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        return postService.updatePostAdmin(postId, postRequestDto, username);
    }

    //게시글 삭제 (일반유저)
    @ApiImplicitParam(name = "postId", value = "게시글 id", dataTypeClass = Integer.class,example="1")
    @ApiOperation(value = "게시글 삭제", notes = "선택한 게시글을 삭제한다.")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<StatusResponseDto> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        StatusResponseDto statusResponseDto = postService.deletePost(postId, username);
        return new ResponseEntity<>(statusResponseDto, headers,HttpStatus.OK);


    }

    //게시글 수정 (관리자)
    @ApiImplicitParam(name = "postId", value = "게시글 id", dataTypeClass = Integer.class,example="1")
    @ApiOperation(value = "게시글 삭제(관리자)", notes = "선택한 게시글을 관리자 권한으로 삭제한다.")
    @Secured(UserRoleEnum.Authority.ADMIN)
    @DeleteMapping("/admin/posts/{postId}")
    public ResponseEntity<StatusResponseDto> deletePostAdmin(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        StatusResponseDto statusResponseDto = postService.deletePostAdmin(postId, username);
        return new ResponseEntity<>(statusResponseDto, headers,HttpStatus.OK);
    }



}
