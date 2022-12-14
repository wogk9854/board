package com.sparta.board.entity;

import com.sparta.board.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column(nullable = false)
    private int commentLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    public Comment(User user, Board board, CommentRequestDto requestDto) {
        this.user = user;
        this.board = board;
        this.content = requestDto.getContent();
    }

    public Comment(User user, Board board, CommentRequestDto requestDto, Comment comment) {
        this.user = user;
        this.board = board;
        this.content = requestDto.getContent();
        this.parent = comment;
        //이렇게해주면
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    public void like() {
        this.commentLike +=1;
    }

    public void dislike() {
        this.commentLike -=1;
    }
}
