package com.sparta.board.service;

import com.sparta.board.dto.CommentRequestDto;
import com.sparta.board.dto.CommentResponseDto;
import com.sparta.board.dto.MsgResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.User;
import com.sparta.board.entity.UserRoleEnum;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final BoardRepository boardRepository;

    //작성
    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto requestDto, User user) {
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("없는글번호입니다.")
            );
            Comment save = new Comment(user, board, requestDto);

            Comment comment = commentRepository.saveAndFlush(save);

            return new CommentResponseDto(comment);

    }
    //수정
    @Transactional
    public ResponseEntity<?> updateComment(Long id, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("없는댓글입니다.")
        );
        if(user.getRole() == UserRoleEnum.ADMIN){
            comment.update(requestDto);
        } else {
            if(!comment.getUser().getUsername().equals(user.getUsername())){
                return ResponseEntity.ok(new MsgResponseDto("작성자만 수정할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
            }
            comment.update(requestDto);
        }
        return ResponseEntity.ok(new CommentResponseDto(comment));

    }
    //삭제
    @Transactional
    public MsgResponseDto deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("없는댓글입니다.")
        );
        if(user.getRole() == UserRoleEnum.ADMIN){
            commentRepository.deleteById(id);
        } else{
            if(!comment.getUser().getUsername().equals(user.getUsername())){
                return new MsgResponseDto("작성자만 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST.value());
            }
            commentRepository.deleteById(id);
        }
        return new MsgResponseDto("댓글삭제완료", HttpStatus.OK.value());
    }
}
