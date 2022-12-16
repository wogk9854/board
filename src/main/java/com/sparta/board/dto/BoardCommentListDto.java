package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardCommentListDto {
    private Long id;

    private String username;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private int boardLike;

    private List<CommentResponseDto> comments;


    public BoardCommentListDto(Board board){
        this.id = board.getId();
        this.username = board.getUser().getUsername();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.comments = board.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
        this.boardLike = board.getBoardLike();
    }
}
