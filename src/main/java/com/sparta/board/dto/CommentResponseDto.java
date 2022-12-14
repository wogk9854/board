package com.sparta.board.dto;

import com.sparta.board.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class CommentResponseDto {
    private String username;
    private String content;
    private int commentLike;
    private LocalDateTime CreatedAt;
    private LocalDateTime ModifiedAt;




    public CommentResponseDto(Comment comment){
        this.username = comment.getUser().getUsername();
        this.content = comment.getContent();
        this.commentLike = comment.getCommentLike();
        this.CreatedAt = comment.getCreatedAt();
        this.ModifiedAt = comment.getModifiedAt();
    }



}
