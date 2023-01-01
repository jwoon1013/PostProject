package com.hannunpalzi.postproject.entity;

import com.hannunpalzi.postproject.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    private String writer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Post post;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CommentLike> like = new ArrayList<>();

    public Comment(CommentRequestDto commentRequestDto, User user, Post post) {
        this.comment = commentRequestDto.getComment();
        this.writer = user.getUsername();
        this.user = user;
        this.post = post;
    }

    public void modifiedComment(CommentRequestDto commentRequestDto){
        this.comment = comment;

    }


}
