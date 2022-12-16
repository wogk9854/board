package com.sparta.board.controller;

import com.sparta.board.dto.MsgResponseDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.BoardLikeService;
import com.sparta.board.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {

    private final BoardLikeService boardLikeService;
    private final CommentLikeService commentLikeService;

    //게시글 좋아요
    @PostMapping("/like/board/{id}")
    public MsgResponseDto likeBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardLikeService.likeBoard(id, userDetails.getUser());
    }


    //댓글 좋아요
    @PostMapping("/like/comment/{id}")
    public MsgResponseDto likeComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.likeComment(id, userDetails.getUser());
    }
}
