package com.sparta.board.repository;

import com.sparta.board.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<BoardLike, Long> {

}
