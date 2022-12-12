package com.sparta.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@Setter
public class BoardRequestDto {
    private String title;
    private String content;
    private boolean admin = false;
    private String adminToken = "";



}
