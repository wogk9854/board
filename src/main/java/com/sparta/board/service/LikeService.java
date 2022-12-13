package com.sparta.board.service;

import com.sparta.board.dto.MsgResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.BoardLike;
import com.sparta.board.entity.User;
import com.sparta.board.repository.BoardLikeRepository;
import com.sparta.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final BoardRepository boardRepository;

    private final BoardLikeRepository boardLikeRepository;

    @Transactional
    public MsgResponseDto boardLike(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("없는글번호입니다.")
        );

        BoardLike boardLike = boardLikeRepository.findByUserAndBoard(user, board).orElse(null);

        if(boardLike == null){
            board.like();
            BoardLike saveLike = new BoardLike(user, board);
            boardLikeRepository.save(saveLike);
            return new MsgResponseDto("최초 좋아요", HttpStatus.OK.value());
        } else {
            if((boardLike.getLikeCheck() % 2) == 1){
                boardLike.update();
                board.likeCancel();
                return new MsgResponseDto("좋아요 취소", HttpStatus.OK.value());
            }
            else{
                board.like();
                boardLike.update();
                return new MsgResponseDto("좋아요", HttpStatus.OK.value());
            }
        }

    }
}
