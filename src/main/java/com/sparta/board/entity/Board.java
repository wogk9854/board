package com.sparta.board.entity;

import com.sparta.board.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//자동으로 숫자가늘어남?
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private int likeCount;


    @OneToMany(mappedBy = "board")
    private List<BoardLike> boardLikes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)//게시글삭제되면댓글도삭제
    @OrderBy("id asc")
    List<Comment> comments;

    public Board(BoardRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = user;
    }


    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }


    public void likeCancel() {
        this.likeCount += -1;
    }

    public void like() {
        this.likeCount += 1;
    }
}
