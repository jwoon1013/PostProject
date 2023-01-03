package com.hannunpalzi.postproject.entity;

import com.hannunpalzi.postproject.dto.ReCommentRequestDto;
import com.hannunpalzi.postproject.dto.ReCommentResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class ReComment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String reComment;
    @Column(nullable = false)
    private String writer;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;
    @OneToMany(mappedBy = "reComment", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ReCommentLike> like = new ArrayList<>();

    public ReComment(ReCommentRequestDto recommentRequestDto, User user, Comment comment) {
        this.reComment = recommentRequestDto.getReComment();
        this.writer = user.getUsername();
        this.user = user;
        this.comment = comment;
    }

    public boolean checkUsernameIsWriter(String username) {
        return this.getWriter().equals(username);
    }

    public void update(String reComment) {
        this.reComment = reComment;
    }
}
