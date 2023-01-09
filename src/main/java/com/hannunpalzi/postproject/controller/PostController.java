package com.hannunpalzi.postproject.controller;

import com.hannunpalzi.postproject.dto.*;
import com.hannunpalzi.postproject.security.UserDetailsImpl;
import com.hannunpalzi.postproject.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Api(tags = {"게시글 CRUD api"})
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

    // 카테고리명으로 소속 게시글 조회
    @ApiImplicitParam(name = "categoryId", value = "카테고리 id", dataTypeClass = Integer.class,example="1")
    @ApiOperation(value = "카테고리 내의 게시글 조회", notes = "선택한 카테고리 내의 게시글을 조회한다.")
    @GetMapping("/categories/{categoryId}/posts")
    public List<PostResponseDto> getPostListByCategoryId(@PathVariable Long categoryId) {
        return postService.getPostListByCategoryId(categoryId);
    }

    @ApiImplicitParam(name = "postId", value = "게시글 id", dataTypeClass = Integer.class,example="1")
    @ApiOperation(value = "게시글 조회", notes = "선택한 게시글을 조회한다.")
    @GetMapping("/posts/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) { return postService.getPost(postId);}

    // 전체 게시글 조회
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "페이지 번호, 이동할 페이지를 입력한다", dataTypeClass = Integer.class,example="1"),
            @ApiImplicitParam(name = "size", value = "페이징을 할 요소의 수, 페이지에 표시되는 요소의 갯수를 입력한다.", dataTypeClass = Integer.class,example="1"),
            @ApiImplicitParam(name = "sortBy", value = "정렬기준. 정렬의 기준이 되는 필드값을 입력한다.", dataTypeClass = String.class,example="createdAt"),
            @ApiImplicitParam(name = "isAsc", value = "오름차순과 내림차순의 여부. true면 오름차순,false면 내림차순", dataTypeClass = Boolean.class,example="true")
    })//ex)http://localhost:8080/posts?sortBy=createdAt&isAsc=true&size=3&page=1
    @ApiOperation(value = "게시글 전체 조회", notes = "게시글을 전부 조회한다.")
    @GetMapping("/posts")
    public List<PostResponseDto> getTotalPostsList(
             @RequestParam int page,
             @RequestParam int size,
             @RequestParam String sortBy,
             @RequestParam boolean isAsc
    )
    { return postService.getTotalPostsList(page-1, size, sortBy,isAsc);}

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
//    @Secured(UserRoleEnum.Authority.ADMIN)
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

    //게시글 삭제 (관리자)
    @ApiImplicitParam(name = "postId", value = "게시글 id", dataTypeClass = Integer.class,example="1")
    @ApiOperation(value = "게시글 삭제(관리자)", notes = "선택한 게시글을 관리자 권한으로 삭제한다.")
//    @Secured(UserRoleEnum.Authority.ADMIN)//hasanyRole로 인가를 제한하면 accessdDeniedHandler로 예외처리를 해야한다. @Secured로 제한하면 컨트롤러까지 오기때문에 전역 예외처리로 예외처리가 가능하다.
    @DeleteMapping("/admin/posts/{postId}")
    public ResponseEntity<StatusResponseDto> deletePostAdmin(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String username = userDetails.getUsername();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        StatusResponseDto statusResponseDto = postService.deletePostAdmin(postId, username);
        return new ResponseEntity<>(statusResponseDto, headers,HttpStatus.OK);
    }



}
