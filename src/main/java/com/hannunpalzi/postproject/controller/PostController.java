package com.hannunpalzi.postproject.controller;

import com.hannunpalzi.postproject.dto.*;
import com.hannunpalzi.postproject.jwtUtil.JwtUtil;
import com.hannunpalzi.postproject.service.PostService;
import com.hannunpalzi.postproject.service.TokenChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;
    private final JwtUtil jwtUtil;
    private final TokenChecker tokenChecker;
    //게시글 작성
    @PostMapping("/posts")
    public CreatedPostResponseDto createPost(@RequestBody PostRequestDto postRequestDto , HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        String username = tokenChecker.checkToken(token);
        return postService.createPost(postRequestDto, username);
    }

    //선택한 게시글 상세 조회
    @GetMapping("/posts/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) { return postService.getPost(postId);}

    // 전체 게시글 조회
    @GetMapping("/posts")
    public List<PostResponseDto> getTotalPostsList() { return postService.getTotalPostsList();}

    //게시글 수정 (일반유저)
    @PutMapping("/posts/{postId}")
    public PostUpdateResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        String username = tokenChecker.checkToken(token);
        return postService.updatePost(postId, postRequestDto, username);
    }

    //게시글 수정 (관리자)
    @PutMapping("/admin/posts/{postId}")
    public PostUpdateResponseDto updatePostAdmin(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        String username = tokenChecker.checkToken(token);
        return postService.updatePostAdmin(postId, postRequestDto, username);
    }

    //게시글 삭제 (일반유저)
    @DeleteMapping("/posts/{postId}")
    public DeleteResponseDto deletePost(@PathVariable Long postId, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        String username = tokenChecker.checkToken(token);
        return postService.deletePost(postId, username);
    }

    //게시글 수정 (관리자)
    @DeleteMapping("/admin/posts/{postId}")
    public DeleteResponseDto deletePostAdmin(@PathVariable Long postId, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        String username = tokenChecker.checkToken(token);
        return postService.deletePostAdmin(postId, username);
    }



}
