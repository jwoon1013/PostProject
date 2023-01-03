package com.hannunpalzi.postproject.service;

import com.hannunpalzi.postproject.dto.ReCommentRequestDto;
import com.hannunpalzi.postproject.dto.ReCommentResponseDto;
import com.hannunpalzi.postproject.dto.StatusResponseDto;
import com.hannunpalzi.postproject.entity.Comment;
import com.hannunpalzi.postproject.entity.ReComment;
import com.hannunpalzi.postproject.entity.User;
import com.hannunpalzi.postproject.repository.CommentRepository;
import com.hannunpalzi.postproject.repository.PostRepository;
import com.hannunpalzi.postproject.repository.ReCommentRepository;
import com.hannunpalzi.postproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReCommentService {
    private final ReCommentRepository reCommentRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 대댓글 작성하기
    @Transactional
    public ReCommentResponseDto createReComment(Long commentId, Long postId, ReCommentRequestDto reCommentRequestDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 ID의 댓글이 존재하지 않습니다.")
        );
        postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 ID의 POST가 존재하지 않습니다.")
        );

        ReComment reComment = new ReComment(reCommentRequestDto, user, comment);
        reCommentRepository.save(reComment);
        return new ReCommentResponseDto(reComment);
    }

    // 대댓글 수정하기 (일반유저)
    @Transactional
    public ReCommentResponseDto updateReComment(Long reCommentId, Long commentId, Long postId, ReCommentRequestDto recommentRequestDto, String username) {
        // (1) 해당 id 대댓글 있는지 확인 후 불러와
        ReComment reComment = reCommentRepository.findById(reCommentId).orElseThrow(
                () -> new IllegalArgumentException("해당 id의 대댓글이 존재하지 않습니다.")
        );
        // 해당 유저 있는지 확인 후 불러와
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        // 해당 id 댓글 있는지 확인
        commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 ID의 댓글이 존재하지 않습니다.")
        );
        // 해당 id 포스트 있는지 확인
        postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 ID의 POST가 존재하지 않습니다.")
        );
        //(2) username이 작성자와 일치하는지 확인
        if (reComment.checkUsernameIsWriter(username)) {
            reComment.update(recommentRequestDto.getReComment());
        } else throw new IllegalArgumentException("본인이 작성한 대댓글만 수정할 수 있습니다.");
        return new ReCommentResponseDto(reComment);
    }

    // 대댓글 수정하기 (관리자)
    @Transactional
    public ReCommentResponseDto updateReCommentAdmin(Long reCommentId, Long commentId, Long postId, ReCommentRequestDto recommentRequestDto, String username) {
        // (1) 해당 id 대댓글 있는지 확인 후 불러와
        ReComment reComment = reCommentRepository.findById(reCommentId).orElseThrow(
                () -> new IllegalArgumentException("해당 id의 대댓글이 존재하지 않습니다.")
        );
        // 해당 유저 있는지 확인 후 불러와
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        // 해당 id 댓글 있는지 확인
        commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 ID의 댓글이 존재하지 않습니다.")
        );
        // 해당 id 포스트 있는지 확인
        postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 ID의 POST가 존재하지 않습니다.")
        );
        reComment.update(recommentRequestDto.getReComment());
        return new ReCommentResponseDto(reComment);
    }

    //대댓글 사용하기 (일반사용자)
    @Transactional
    public StatusResponseDto deleteReComment(Long reCommentId, Long commentId, Long postId, String username) {
        // (1) 해당 id 대댓글 있는지 확인 후 불러와
        ReComment reComment = reCommentRepository.findById(reCommentId).orElseThrow(
                () -> new IllegalArgumentException("해당 id의 대댓글이 존재하지 않습니다.")
        );
        // 해당 유저 있는지 확인 후 불러와
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        // 해당 id 댓글 있는지 확인
        commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 ID의 댓글이 존재하지 않습니다.")
        );
        // 해당 id 포스트 있는지 확인
        postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 ID의 POST가 존재하지 않습니다.")
        );
        //(2) username이 작성자와 일치하는지 확인
        if (reComment.checkUsernameIsWriter(username)) {
            reCommentRepository.deleteById(reCommentId);
        } else throw new IllegalArgumentException("본인이 작성한 대댓글만 삭제할 수 있습니다.");
        return new StatusResponseDto(HttpStatus.OK.value(), "대댓글 삭제 완료");
    }

    @Transactional
    public StatusResponseDto deleteReCommentAdmin(Long reCommentId, Long commentId, Long postId, String username) {
        // (1) 해당 id 대댓글 있는지 확인 후 불러와
        ReComment reComment = reCommentRepository.findById(reCommentId).orElseThrow(
                () -> new IllegalArgumentException("해당 id의 대댓글이 존재하지 않습니다.")
        );
        // 해당 유저 있는지 확인 후 불러와
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        // 해당 id 댓글 있는지 확인
        commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 ID의 댓글이 존재하지 않습니다.")
        );
        // 해당 id 포스트 있는지 확인
        postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 ID의 POST가 존재하지 않습니다.")
        );
        reCommentRepository.deleteById(reCommentId);
        return new StatusResponseDto(HttpStatus.OK.value(), "대댓글 삭제 완료");
    }


}
