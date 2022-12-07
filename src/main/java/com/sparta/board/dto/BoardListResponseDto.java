package com.sparta.board.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardListResponseDto {

    List<BoardResponseDto> boardList = new ArrayList<>();
    public void addBoard(BoardResponseDto boardResponseDto) {

        boardList.add(boardResponseDto);
    }
}
