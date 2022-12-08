package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.MsgResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.entity.User;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import com.sparta.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
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
//    @Transactional(readOnly = true)
//    public BoardResponseDto detailBoard(Long id, HttpServletRequest request) {
//        // Request에서 Token 가져오기
//        String token = jwtUtil.resolveToken(request);
//        Claims claims;
//
//        // 토큰이 있는 경우에만 관심상품 조회 가능
//        if (token != null) {
//            // Token 검증
//            if (jwtUtil.validateToken(token)) {
//                // 토큰에서 사용자 정보 가져오기
//                claims = jwtUtil.getUserInfoFromToken(token);
//            } else {
//                throw new IllegalArgumentException("Token Error");
//            }
//
//            Board board = boardRepository.findById(id).orElseThrow(
//                    () -> new IllegalArgumentException("없는글번호입니다.")
//            );
//            return new BoardResponseDto(board);
//
//        } else {
//            throw new IllegalArgumentException("토큰이없습니다.");
//        }
//    }


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


//    @Transactional(readOnly = true)
//    public List<BoardResponseDto> readBoard() {
//
//            List<BoardResponseDto> list = new ArrayList<>();
//            List<Board> boardList;
//
//            boardList = boardRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId());
//
//            for (Board board : boardList) {
//                list.add(new BoardResponseDto(board));
//            }
//
//
//    }


    //작성
//    @Transactional
//    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
//        Board board = new Board(requestDto);
//        boardRepository.save(board);
//        return new BoardResponseDto(board);
//    }
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 추가 가능
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

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Board board = boardRepository.saveAndFlush(new Board(requestDto, user));

            return new BoardResponseDto(board);
        } else {
            throw new IllegalArgumentException("토큰이없습니다.");
        }
    }


    //수정
    @Transactional
    public BoardResponseDto update(Long id, BoardRequestDto requestDto, HttpServletRequest request) {
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
                    () -> new IllegalArgumentException("아이디가 없습니다.")
            );

//            System.out.println("보드겟아이디 -- " + board.getUsername());
            System.out.println("유저겟아이디 -- " + user.getUsername());
            System.out.println(board.getId());

            if(board.getUser().getUsername().equals(user.getUsername())){
                board.update(requestDto);
            } else{
                throw new IllegalArgumentException("본인이 작성한글만 수정가능합니다.");
            }

            return new BoardResponseDto(board);
        } else {
            throw new IllegalArgumentException("토큰이없습니다.");
        }

//        Board board = boardRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 없습니다.")
//        );
//        boolean result = requestDto.getPassword().equals(board.getPassword());
//        System.out.println(result);
//        if (result) {
//            board.update(requestDto);
//        } else {
//            System.out.println("비밀번호가일치하지않습니다.");
//        }
//        return new BoardResponseDto(board);
    }

    //삭제
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

            if(board.getUser().getUsername().equals(user.getUsername())){
                boardRepository.deleteById(id);
            } else{
                throw new IllegalArgumentException("본인이 작성한글만 삭제가능합니다.");
            }

            return new MsgResponseDto("게시글삭제완료", HttpStatus.OK.value());
        } else {
            throw new IllegalArgumentException("토큰이없습니다.");
        }
    }





}
