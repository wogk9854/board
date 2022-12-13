package com.sparta.board.repository;

import com.sparta.board.entity.Comment;
import com.sparta.board.entity.CommentLike;
import com.sparta.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByUserAndComment(User user, Comment comment);


}
