package com.hannunpalzi.postproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class ReCommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String username;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ReComment reComment;

    public ReCommentLike(String username, ReComment reComment) {
        this.username = username;
        this.reComment = reComment;
    }
}
