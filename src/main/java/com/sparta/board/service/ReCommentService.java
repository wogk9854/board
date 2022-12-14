package com.sparta.board.service;

import com.sparta.board.dto.*;
import com.sparta.board.entity.*;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.CommentRepository;
import com.sparta.board.repository.ReCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReCommentService {
    private final ReCommentRepository reCommentRepository;

    private final CommentRepository commentRepository;

    //작성
    @Transactional
    public ReCommentResponseDto reCreateComment(Long id, ReCommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없는댓글번호입니다.")
        );
        ReComment save = new ReComment(user, comment, requestDto);

        ReComment reComment = reCommentRepository.saveAndFlush(save);

        return new ReCommentResponseDto(reComment);

    }
    //수정
//    @Transactional
//    public ResponseEntity<?> updateComment(Long id, CommentRequestDto requestDto, User user) {
//        Comment comment = commentRepository.findById(id).orElseThrow(
//                ()-> new IllegalArgumentException("없는댓글입니다.")
//        );
//        if(user.getRole() == UserRoleEnum.ADMIN){
//            comment.update(requestDto);
//        } else {
//            if(!comment.getUser().getUsername().equals(user.getUsername())){
//                return ResponseEntity.ok(new MsgResponseDto("작성자만 수정할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
//            }
//            comment.update(requestDto);
//        }
//        return ResponseEntity.ok(new CommentResponseDto(comment));
//
//    }
//    //삭제
//    @Transactional
//    public MsgResponseDto deleteComment(Long id, User user) {
//        Comment comment = commentRepository.findById(id).orElseThrow(
//                ()-> new IllegalArgumentException("없는댓글입니다.")
//        );
//        if(user.getRole() == UserRoleEnum.ADMIN){
//            commentRepository.deleteById(id);
//        } else{
//            if(!comment.getUser().getUsername().equals(user.getUsername())){
//                return new MsgResponseDto("작성자만 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST.value());
//            }
//            commentRepository.deleteById(id);
//        }
//        return new MsgResponseDto("댓글삭제완료", HttpStatus.OK.value());
//    }
}
