package com.sparta.board.entity;

import com.sparta.board.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)//댓글 삭제되면대댓글도삭제
    @OrderBy("id asc")
    List<ReComment> reComments;

    public Comment(User user, Board board, CommentRequestDto requestDto) {
        this.user = user;
        this.board = board;
        this.content = requestDto.getContent();
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
