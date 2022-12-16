package com.sparta.board.dto;

import com.sparta.board.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReCommentListDto {
    private Long id;

    private String username;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public ReCommentListDto(Comment comment){
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }


}
