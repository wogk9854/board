package com.sparta.board.repository;

import com.sparta.board.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    Optional<BoardLike>  findByUserAndBoard(User user, Board board);
}

