package com.sparta.board.repository;

import com.sparta.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
//    List<Board> findAllByOrderByModifiedAtDesc();
    Board deleteAllBy();
    List<Board> findAllByOrderByCreatedAtDesc();
//    Optional<Board> findByUsernameOrderByModifiedAtDesc(String username);
//
//    List<Board> findAllByUserIdOrderByModifiedAtDesc(Long userId);

//    List<Board> findAllByUserIdOrderByCreatedAtDesc(Long userId);


}
