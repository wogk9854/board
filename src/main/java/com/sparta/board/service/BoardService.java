package com.sparta.board.service;

import com.sparta.board.dto.BoardCommentListDto;
import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.MsgResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.User;
import com.sparta.board.entity.UserRoleEnum;

import com.sparta.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {



    private final BoardRepository boardRepository;


    private static String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    //글상세조회
    @Transactional(readOnly = true)
    public BoardResponseDto detailBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없는글번호입니다.")
        );
        return new BoardResponseDto(board);
    }


    //전체글조회
    @Transactional(readOnly = true)
    public List<BoardCommentListDto> readBoard() {
        List<Board> boards = boardRepository.findAllByOrderByCreatedAtDesc();

        return boards.stream().map(b -> new BoardCommentListDto(b)).collect(Collectors.toList());
    }

    //작성
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        Board board = boardRepository.saveAndFlush(new Board(requestDto, user));
        return new BoardResponseDto(board);
    }

    //수정
    @Transactional
    public ResponseEntity<?> update(Long id, BoardRequestDto requestDto, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없는글번호입니다.")
        );

        if (user.getRole() == UserRoleEnum.ADMIN) {
            board.update(requestDto);//admin이면무조건수정
        } else {
            if (!board.getUser().getUsername().equals(user.getUsername())) {
                return ResponseEntity.ok(new MsgResponseDto("작성자만 수정할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
            }
            board.update(requestDto);//본인글만수정
        }
        return ResponseEntity.ok(new BoardResponseDto(board));
    }

    //삭제
    @Transactional
    public MsgResponseDto deleteBoard(Long id,User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없는게시글번호입니다.")
        );

        if(user.getRole() == UserRoleEnum.ADMIN){
            boardRepository.deleteById(id);
        }else {
            if(!board.getUser().getUsername().equals(user.getUsername())){
                return new MsgResponseDto("본인글만 삭제하세요",HttpStatus.BAD_REQUEST.value());
            }
            boardRepository.deleteById(id);
        }
        return new MsgResponseDto("삭제 성공",HttpStatus.OK.value());
    }

}

