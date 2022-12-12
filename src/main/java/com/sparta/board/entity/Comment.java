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



    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();

    }
}
