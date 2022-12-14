package com.sparta.board.controller;

import com.sparta.board.dto.*;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.CommentService;
import com.sparta.board.service.ReCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReCommentController {

    private final ReCommentService reCommentService;

    //작성
    @PostMapping("/recomment/{id}")
    public ReCommentResponseDto reCreateComment(@PathVariable Long id, @RequestBody ReCommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return reCommentService.reCreateComment(id, requestDto, userDetails.getUser());
    }
    //수정
//    @PutMapping("comment/{id}")
//    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return commentService.updateComment(id, requestDto, userDetails.getUser());
//    }
//    //삭제
//    @DeleteMapping("comment/{id}")
//    public MsgResponseDto deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return commentService.deleteComment(id, userDetails.getUser());
//    }


}

