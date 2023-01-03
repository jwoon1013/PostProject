package com.hannunpalzi.postproject.service;

import com.hannunpalzi.postproject.dto.*;
import com.hannunpalzi.postproject.entity.Post;
import com.hannunpalzi.postproject.entity.User;
import com.hannunpalzi.postproject.jwtUtil.JwtUtil;
import com.hannunpalzi.postproject.repository.PostRepository;
import com.hannunpalzi.postproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시글 작성
    @Transactional
    public CreatedPostResponseDto createPost(PostRequestDto postRequestDto, String username) {
        // username 을 통해 post 의 writer 가 될 user 생성
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 username 의 사용자가 존재하지 않음")
        );
        //post 생성 후 레파지토리에 저장
        Post post = postRequestDto.toEntity(user);
        postRepository.save(post);
        //PostResponseDto 생성 후 리턴
        return new CreatedPostResponseDto(post);
    }

    //선택한 게시글 상세 조회
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("id값이 일치하는 게시글이 없습니다.")
        );
        return new PostResponseDto(post);
    }

    //전체 게시글 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> getTotalPostsList() {
        List<Post> totalPostsList = postRepository.findAllByOrderByModifiedAtDesc();
        return totalPostsList.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

    }

    // 게시글 수정 (일반유저)
    @Transactional
    public PostUpdateResponseDto updatePost(Long postId, PostRequestDto postRequestDto, String username) {
        // 1. 해당 postId의 포스트가 있는지 확인 후 불러오기
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 postId의 포스트가 존재하지 않습니다")
        );
        // 2. 로그인한 username의 유저 생성
        userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );
        // 3. user의 username과 post의 writer 가 일치하는지 확인
        if (post.checkUsernameIsWriter(username)) {
            // 4. 일치하는 경우 수정 진행
            post.update(postRequestDto.getTitle(), postRequestDto.getContents());
        } else throw new IllegalArgumentException("본인이 작성한 게시글만 수정할 수 있습니다.");

        // 5. postResponseDto 생성해서 리턴
        return new PostUpdateResponseDto(post);

    }

    // 게시글 수정 (관리자)
    @Transactional
    public PostUpdateResponseDto updatePostAdmin(Long postId, PostRequestDto postRequestDto, String username) {
        // 1. 해당 postId의 포스트가 있는지 확인 후 불러오기
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 postId의 포스트가 존재하지 않습니다")
        );
        // 2. 로그인한 username의 유저 생성
        userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );
        // 3. 수정 진행
        post.update(postRequestDto.getTitle(), postRequestDto.getContents());
        // 4. postResponseDto 생성해서 리턴
        return new PostUpdateResponseDto(post);
    }

    @Transactional
    // 선택한 게시글 삭제(일반유저)
    public StatusResponseDto deletePost(Long postId, String username) {
        //1. 해당 post 있는지 확인 후 불러와
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 postId의 포스트가 존재하지 않습니다")
        );
        // 2. 로그인한 username의 유저 생성
        userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );

        //3. 작성자와 로그인유저가 일치하는지 확인해
        if (post.checkUsernameIsWriter(username)) {
            //4. 일치할 시 삭제 진행
            postRepository.deleteById(postId);
        } else throw new IllegalArgumentException("본인이 작성한 게시글만 삭제할 수 있습니다");

        //5. StatusResponseDto 반환
        return new StatusResponseDto(HttpStatus.OK.value(), "게시글 삭제 완료");
    }

    // 선택한 게시글 삭제(관리자)
    @Transactional
    public StatusResponseDto deletePostAdmin(Long postId, String username) {
        //1. 해당 post 있는지 확인 후 불러와
        postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 postId의 포스트가 존재하지 않습니다")
        );
        // 2. 로그인한 username의 유저 생성
        userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );
        //3. 삭제 진행
        postRepository.deleteById(postId);

        //4. StatusResponseDto 반환
        return new StatusResponseDto(HttpStatus.OK.value(), "게시글 삭제 완료");
    }
}
