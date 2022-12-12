package com.sparta.board.dto;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;

    //관리자 회원가입 코드 - 성현
    private boolean admin = false;
    private String adminToken = "";
}
