package com.sparta.board.controller;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.MsgResponseDto;
import com.sparta.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;





//    //생성
//    @PostMapping("/board")
//    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto) {
//        return boardService.createBoard(requestDto);
//    }

    @PostMapping("/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        // 응답 보내기
        return boardService.createBoard(requestDto, request);
    }




//    //조회
//    @GetMapping("/board")
//    public List<BoardResponseDto> readBoard() {
//
//        return boardService.readBoard();
//    }

    //조회하기
    @GetMapping("/board")
    public List<BoardResponseDto> readBoard() {
        // 응답 보내기
        return boardService.readBoard();
    }

    //상세조회
    @GetMapping("/detail/{id}")
    public BoardResponseDto detailBoard(@PathVariable Long id) {

        return boardService.detailBoard(id);
    }




    //수정
    @PutMapping("/update/{id}")
    public BoardResponseDto update(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, HttpServletRequest request) {
        return boardService.update(id, requestDto, request);
    }

    //삭제
    @DeleteMapping("/delete/{id}")
    public MsgResponseDto deleteBoard(@PathVariable Long id, HttpServletRequest request) {
        return boardService.deleteBoard(id, request);
    }




}
