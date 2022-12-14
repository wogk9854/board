package com.sparta.board.service;

import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.MsgResponseDto;
import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.entity.User;
import com.sparta.board.entity.UserRoleEnum;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    //회원가입
    @Transactional
    public MsgResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();//pw 인코딩 - 성현

        //아이디유효성검사
        if (Pattern.matches( "^[a-z0-9]*$",username)){
            if(username.length() < 4 || username.length() > 10){
//                throw new IllegalArgumentException("아이디 길이를 4자 이상 10자 이하로 해주세요");
                return new MsgResponseDto("아이디 길이를 4자 이상 10자 이하로 해주세요", HttpStatus.BAD_REQUEST.value());
            }

            //중복확인
            Optional<User> found = userRepository.findByUsername(username);
            if (found.isPresent()){
                return new MsgResponseDto("중복된 아이디입니다.", HttpStatus.BAD_REQUEST.value());

            }
        }else{
            return new MsgResponseDto("아이디를 알파벳소문자 또는 숫자로만 구성해주세요", HttpStatus.BAD_REQUEST.value());
        }

        //비밀번호유효성검사
        if (Pattern.matches( "^.(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",password)){
            if(password.length() < 8 || password.length() > 15){
                return new MsgResponseDto("비밀번호 길이를 8자 이상 15자 이하로 해주세요", HttpStatus.BAD_REQUEST.value());
            }
        } else {
            return new MsgResponseDto("비밀번호에 알파벳대소문자, 숫자, 특수문자가 포함되게 해주세요", HttpStatus.BAD_REQUEST.value());
        }
        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        //pw암호화
        password = passwordEncoder.encode(password);

        User user = new User(username, password, role);
        userRepository.save(user);

        return new MsgResponseDto("회원가입성공", HttpStatus.OK.value());
    }


    @Transactional(readOnly = true)
    public MsgResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        System.out.println(password);
        //사용자확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 아이디가 아닙니다.")

        );

        if(!passwordEncoder.matches(password, user.getPassword())){

            throw new IllegalArgumentException("비밀번호를 확인해주세요");

        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));

        return new MsgResponseDto("로그인성공", HttpStatus.OK.value());
    }

}



