package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.MsgResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.User;
import com.sparta.board.entity.UserRoleEnum;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;


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
    public List<BoardResponseDto> readBoard() {
//        BoardListResponseDto boardListResponseDto = new BoardListResponseDto();
        List<Board> boards = boardRepository.findAllByOrderByCreatedAtDesc();
//        List<BoardResponseDto> collect =
//        for(Board board : boards){
//            boardListResponseDto.addBoard(new BoardResponseDto(board));
//        }
        return boards.stream().map(b -> new BoardResponseDto(b)).collect(Collectors.toList());
    }

    //작성
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Board board = boardRepository.saveAndFlush(new Board(requestDto, user));
        return new BoardResponseDto(board);
    }

    //수정
    @Transactional
    public BoardResponseDto update(Long id, BoardRequestDto requestDto, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없는글번호입니다.")
        );

        if (user.getRole() == UserRoleEnum.ADMIN) {
            board.update(requestDto);//admin이면무조건수정
        } else {
            if (board.getUser().getUsername().equals(user.getUsername())) {
                board.update(requestDto);//본인글만수정
            } else {
                throw new IllegalArgumentException("본인이 작성한글만 수정가능합니다.");
            }
        }
        return new BoardResponseDto(board);
    }

    //삭제
    public MsgResponseDto deleteBoard(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없는게시글번호입니다.")
        );

        if (user.getRole() == UserRoleEnum.ADMIN) {
            boardRepository.deleteById(id);//admin이면무조건삭제
        } else {
            if (board.getUser().getUsername().equals(user.getUsername())) {
                boardRepository.deleteById(id);//본인글만삭제
            } else {
                throw new IllegalArgumentException("본인이 작성한글만 삭제가능합니다.");
            }
        }

        return new MsgResponseDto("게시글삭제완료", HttpStatus.OK.value());

    }


}
