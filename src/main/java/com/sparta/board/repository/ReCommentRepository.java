package com.sparta.board.repository;

import com.sparta.board.entity.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReCommentRepository extends JpaRepository<ReComment, Long> {
//    List<Comment> findAllByOrderByCreatedAtDesc();
}
