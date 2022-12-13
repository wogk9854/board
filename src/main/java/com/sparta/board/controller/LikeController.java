package com.sparta.board.controller;

import com.sparta.board.dto.MsgResponseDto;
import com.sparta.board.security.UserDetailsImpl;
import com.sparta.board.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("board/like/{id}")
    public MsgResponseDto boardLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return likeService.boardLike(id, userDetails.getUser());
    }

}
