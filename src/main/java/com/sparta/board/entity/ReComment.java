package com.sparta.board.entity;

import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.ReCommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ReComment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public ReComment(User user, Comment comment, ReCommentRequestDto requestDto) {
        this.user = user;
        this.comment = comment;
        this.content = requestDto.getContent();
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
