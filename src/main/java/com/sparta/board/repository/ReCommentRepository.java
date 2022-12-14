package com.sparta.board.repository;

import com.sparta.board.entity.Comment;
import com.sparta.board.entity.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReCommentRepository extends JpaRepository<ReComment, Long> {
//    List<Comment> findAllByOrderByCreatedAtDesc();
}
