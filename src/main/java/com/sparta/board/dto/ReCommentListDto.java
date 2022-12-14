package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReCommentListDto {
    private Long id;

    private String username;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private List<ReCommentResponseDto> comments;

    public ReCommentListDto(Comment comment){
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.comments = comment.getReComments().stream().map(ReCommentResponseDto::new).collect(Collectors.toList());
    }
}
