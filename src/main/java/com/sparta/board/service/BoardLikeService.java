package com.sparta.board.service;


import com.sparta.board.dto.MsgResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.BoardLike;
import com.sparta.board.entity.User;
import com.sparta.board.repository.BoardLikeRepository;
import com.sparta.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardLikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;

    //게시글 좋아요 눌렀을 때 홀,짝 판별해서 1넣을지 0넣을지 하는 것
    @Transactional
    public MsgResponseDto likeBoard(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없는 글번호입니다.")
        );

        BoardLike boardLike = boardLikeRepository.findByUserAndBoard(user, board).orElse(null);
        if(boardLike == null) {
            board.like();
            BoardLike boardLike1 = new BoardLike(board, user);
            boardLikeRepository.save(boardLike1);
            return new MsgResponseDto("좋아요 성공!", 200);
        } else {
            if((boardLike.getCountLike()%2)==1) {
                board.dislike();
                boardLike.update();
//                boardLikeRepository.save(boardLike);

                return new MsgResponseDto("좋아요 취소!", 200);
            } else {
                board.like();
                boardLike.update();
//                boardLikeRepository.save(boardLike);
                return new MsgResponseDto("좋아요!", 200);
            }
        }

    }

}
