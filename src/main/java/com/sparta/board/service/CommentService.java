package com.sparta.board.service;

import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.dto.MsgResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.User;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final BoardRepository boardRepository;

    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto requestDto, User user) {
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("없는글번호입니다.")
            );
            Comment save = new Comment(user, board, requestDto);

            Comment comment = commentRepository.saveAndFlush(save);

            return new CommentResponseDto(comment);

    }
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, User user) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("없는댓글입니다.")
        );

        comment.update(requestDto);

        return new CommentResponseDto(comment);

    }

    @Transactional
    public MsgResponseDto deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("없는댓글입니다.")
        );
        commentRepository.deleteById(id);
        return new MsgResponseDto("댓글삭제완료", HttpStatus.OK.value());
    }
}
