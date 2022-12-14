package com.sparta.board.controller;

import com.sparta.board.dto.BoardCommentListDto;
import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.MsgResponseDto;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.entity.User;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;



    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //글작성
    @PostMapping("/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 응답 보내기
        return boardService.createBoard(requestDto, userDetails.getUser());
    }

    //조회하기
    @GetMapping("/board")
    public List<BoardCommentListDto> readBoard() {
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.update(id, requestDto, userDetails.getUser());
    }




    @DeleteMapping("/delete/{id}")
    public MsgResponseDto deleteBoard(@PathVariable  Long id, @AuthenticationPrincipal UserDetailsImpl userDetails ) {
        return boardService.deleteBoard(id,userDetails.getUser());
    }
}
