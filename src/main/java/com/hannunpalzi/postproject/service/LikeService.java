package com.hannunpalzi.postproject.service;

import com.hannunpalzi.postproject.dto.LikeRequestDto;
import com.hannunpalzi.postproject.dto.StatusResponseDto;
import com.hannunpalzi.postproject.entity.*;
import com.hannunpalzi.postproject.repository.*;
import com.hannunpalzi.postproject.responseMessage.ResponseMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ResponseMessageService responseMessageService;
    private final ReCommentRepository reCommentRepository;
    private final ReCommentLikeRepository reCommentLikeRepository;

    @Transactional
    public ResponseEntity<StatusResponseDto> postLike(String username, Long id, LikeRequestDto likeRequestDto){
        if(likeRequestDto.isLike()) {
            Post post = postRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
            );
            Optional<PostLike> found = postLikeRepository.findByUsername(username);
            if (found.isPresent()) {
                postLikeRepository.deleteByUsername(username);
                post.minusLike();
                return responseMessageService.likeCancel();
            } else {
                PostLike postLike = new PostLike(username, post);
                post.plusLike();
                postLikeRepository.save(postLike);
                return responseMessageService.likeOk();
            }
        }
        throw new IllegalArgumentException("오류입니다.");
    }
    @Transactional
    public ResponseEntity<StatusResponseDto> commentLike(String username, Long postId, Long commentId, LikeRequestDto likeRequestDto){
        if(likeRequestDto.isLike()) {
            Comment comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
            );
            Optional<CommentLike> found = commentLikeRepository.findByUsername(username);
            if (found.isPresent()) {
                commentLikeRepository.deleteByUsername(username);
                comment.minusLike();
                return responseMessageService.likeCancel();
            } else {
                CommentLike commentLike = new CommentLike(username,comment,postId);
                comment.plusLike();
                commentLikeRepository.save(commentLike);
                return responseMessageService.likeOk();
            }
        }
        throw new IllegalArgumentException("오류입니다.");
    }

    @Transactional
    public ResponseEntity<StatusResponseDto> reCommentLike(String username, Long id, LikeRequestDto likeRequestDto) {
        if(likeRequestDto.isLike()){
            ReComment reComment = reCommentRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("대댓글이 존재하지 않습니다.")
            );
            Optional<ReCommentLike> found = reCommentLikeRepository.findByUsername(username);
            if(found.isPresent()){
                reCommentLikeRepository.deleteByUsername(username);
                return responseMessageService.likeCancel();
            } else{
                ReCommentLike reCommentLike = new ReCommentLike(username, reComment);
                reCommentLikeRepository.save(reCommentLike);
                return responseMessageService.likeOk();
            }
        }
        throw new IllegalArgumentException("오류입니다.");
    }
}