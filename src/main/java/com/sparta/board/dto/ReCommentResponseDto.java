package com.sparta.board.dto;

import com.sparta.board.entity.Comment;
import com.sparta.board.entity.ReComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
public class ReCommentResponseDto {
    private String username;
    private String content;
    private int commentLike;
    private LocalDateTime CreatedAt;
    private LocalDateTime ModifiedAt;


    public ReCommentResponseDto(ReComment reComment) {
        this.username = reComment.getUser().getUsername();
        this.content = reComment.getContent();
        this.CreatedAt = reComment.getCreatedAt();
        this.ModifiedAt = reComment.getModifiedAt();
    }



}
