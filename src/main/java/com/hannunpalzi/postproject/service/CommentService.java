package com.hannunpalzi.postproject.service;


import com.hannunpalzi.postproject.dto.*;
import com.hannunpalzi.postproject.entity.Comment;
import com.hannunpalzi.postproject.entity.User;
import com.hannunpalzi.postproject.entity.Post;
import com.hannunpalzi.postproject.repository.CommentRepository;
import com.hannunpalzi.postproject.repository.PostRepository;
import com.hannunpalzi.postproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    // 코멘트 작성하기
    @Transactional
    public CommentCreatResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, String username){
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다.")
        );
        Comment comment = new Comment(commentRequestDto, user, post );
        commentRepository.save(comment);
        return new CommentCreatResponseDto(comment);
    }

    // 코멘트 수정하기 (일반)
    @Transactional
    public CommentUpdateResponseDto updateComment(Long commentId, Long postId, CommentRequestDto commentRequestDto, String username){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        userRepository.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );
        postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다.")
        );
        if (comment.checkUserNameIsWriter(username)){
            comment.updateComment(commentRequestDto.getComment());
        }else throw new IllegalArgumentException("본인이 작성한 댓글만 수정할 수 있습니다.");

        return new CommentUpdateResponseDto(comment);
    }

    // 코멘트 수정하기 (관리자)
    @Transactional
    public CommentUpdateResponseDto updateCommentAdmin(Long commentId, Long postId, CommentRequestDto commentRequestDto, String username){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );
        postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다.")
        );
        comment.updateComment(commentRequestDto.getComment());
        return new CommentUpdateResponseDto(comment);
    }

    // 코멘트 삭제하기 (일반)
    @Transactional
    public StatusResponseDto deleteComment(Long commentId, Long postId, String username){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );
        postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        if (comment.checkUserNameIsWriter(username)){
            commentRepository.deleteById(commentId);
        }else throw new IllegalArgumentException("본인이 작성한 댓글만 삭제할 수 있습니다.");

        return new StatusResponseDto(200, "댓글을 삭제하였습니다.");
    }

    @Transactional
    public StatusResponseDto deleteCommentAdmin(Long commentId, Long postId, String username){
        commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );
        userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );
        postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
        );
        commentRepository.deleteById(commentId);
        return new StatusResponseDto(200, "댓글을 삭제하였습니다.");
    }
}
