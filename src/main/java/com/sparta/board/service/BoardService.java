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
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {



    private final BoardRepository boardRepository;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;



    private static String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    //글상세조회
    @Transactional(readOnly = true)
    public BoardResponseDto detailBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없는글번호입니다.")
        );
        return new BoardResponseDto(board);
    }



    @Transactional(readOnly = true)
    public List<BoardResponseDto> readBoard() {

        List<Board> boards = boardRepository.findAllByOrderByCreatedAtDesc();

        return boards.stream().map(b -> new BoardResponseDto()).collect(Collectors.toList());
    }

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
                ()->new IllegalArgumentException("게시글이 없습니다")
        );
        //무조건 업데이트
        if(user.getRole()==UserRoleEnum.ADMIN){
            board.update(requestDto);
        }else{
            if (!board.getUser().getUsername().equals(user.getUsername())){
                throw new IllegalArgumentException("작성자만 수정가능 합니다.");
            }
            board.update(requestDto);
        }

        return new BoardResponseDto(board);
    }


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
            return new MsgResponseDto("삭제 성공",200);
        }


    }
