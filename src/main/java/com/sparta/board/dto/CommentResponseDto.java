package com.sparta.board.dto;

import com.sparta.board.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
public class CommentResponseDto {
    private String username;
    private String content;
    private LocalDateTime CreatedAt;
    private LocalDateTime ModifiedAt;

    private Long boardId;

    public CommentResponseDto(Comment comment){
        this.username = comment.getUser().getUsername();
        this.content = comment.getContent();
        this.CreatedAt = comment.getCreatedAt();
        this.ModifiedAt = comment.getModifiedAt();
        this.boardId = comment.getBoard().getId();
    }



}
