package com.sparta.board.dto;

import com.sparta.board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponseDto {
    private String title;
    private String username;
    private String content;
    private LocalDateTime CreatedAt;
    private LocalDateTime ModifiedAt;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.username = board.getUser().getUsername();
        this.content = board.getContent();
        this.CreatedAt = board.getCreatedAt();
        this.ModifiedAt = board.getModifiedAt();
    }


}
