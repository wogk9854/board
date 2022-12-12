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

    //bean으로 동록된 BCryptPasswordEncoder 의존성 주입

    private final BoardRepository boardRepository;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

//    private final PasswordEncoder passwordEncoder;

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
    public List<BoardResponseDto> readBoard() {
//        BoardListResponseDto boardListResponseDto = new BoardListResponseDto();
        List<Board> boards = boardRepository.findAllByOrderByCreatedAtDesc();
//        List<BoardResponseDto> collect =
//        for(Board board : boards){
//            boardListResponseDto.addBoard(new BoardResponseDto(board));
//        }
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



    //삭제
    @Transactional
    public MsgResponseDto deleteBoard(Long id, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("없는게시글번호입니다.")
            );

            if (board.getUser().getUsername().equals(user.getUsername())) {
                boardRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("본인이 작성한글만 삭제가능합니다.");
            }

            return new MsgResponseDto("게시글삭제완료", HttpStatus.OK.value());
        } else {
            throw new IllegalArgumentException("토큰이없습니다.");
        }
    }
}


