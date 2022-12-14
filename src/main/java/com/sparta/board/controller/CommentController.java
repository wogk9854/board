package com.sparta.board.controller;

import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.dto.MsgResponseDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //작성
    @PostMapping("/comment/{id}")
    public String createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.createComment(id, requestDto, userDetails.getUser());
        return "성공";
    }
    //수정
    @PutMapping("comment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(id, requestDto, userDetails.getUser());
    }
    //삭제
    @DeleteMapping("comment/{id}")
    public MsgResponseDto deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails.getUser());
    }


}
