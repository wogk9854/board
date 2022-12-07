package com.sparta.board.entity;

import com.sparta.board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//자동으로 숫자가늘어남?
    private Long id;

    @Column(nullable = false)
    private String title;
//    @Column(nullable = false)
//    private String username;
    @Column(nullable = false)
    private String content;


    //한유저가 여러글을쓸수있다 ManyToOne??
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Board(BoardRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
//        this.username = user.getUsername();
        this.content = requestDto.getContent();
        this.user = user;
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}
