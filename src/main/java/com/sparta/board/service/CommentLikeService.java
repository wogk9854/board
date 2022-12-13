package com.sparta.board.service;

import com.sparta.board.dto.MsgResponseDto;
import com.sparta.board.entity.Comment;
import com.sparta.board.entity.CommentLike;
import com.sparta.board.entity.User;
import com.sparta.board.repository.CommentLikeRepository;
import com.sparta.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    //댓글 좋아요 눌렀을 때 홀,짝 판별해서 1넣을지 0넣을지 하는 것
    @Transactional
    public MsgResponseDto likeComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없는 댓글 번호입니다.")
        );

        CommentLike commentLike = commentLikeRepository.findByUserAndComment(user, comment).orElse(null);
        if(commentLike == null) {
            comment.like();
            CommentLike commentLike1 = new CommentLike(comment, user);
            commentLikeRepository.save(commentLike1);
            return new MsgResponseDto("댓글 좋아요 구독 알림설정까지~~!", 200);
        } else {
            if((commentLike.getCountLike()%2==1)) {
                comment.dislike();
                commentLike.update();

                return new MsgResponseDto("댓글 좋아요 구독 알림설정까지 취소", 200);
            } else {
                comment.like();
                commentLike.update();
                return new MsgResponseDto("다시 댓글 좋아요 구독 알림설정까지~~!", 200);
            }
        }
    }
}
