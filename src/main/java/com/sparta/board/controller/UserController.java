package com.sparta.board.controller;


import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.MsgResponseDto;
import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService; //의존성 주입
    //회원가입
    @PostMapping("/signup")
    public MsgResponseDto signup(@RequestBody SignupRequestDto signupRequestDto){
        userService.signup(signupRequestDto);
        MsgResponseDto msgResponseDto = new MsgResponseDto("회원가입 완료", 200);
        return msgResponseDto;
    }


    //    @PostMapping("/signup")
//    public String signup(@RequestBody SignupRequestDto signupRequestDto){
//        userService.signup(signupRequestDto);
//        String msg = "회원가입 완료";
//        return  msg;
//    }
    @PostMapping("/login")
    public MsgResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return userService.login(loginRequestDto, response);
    }



}
