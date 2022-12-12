package com.sparta.board.dto;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;

    // 관리자 권한용 리그오브레전드 추가  - 상정
    private boolean admin = false;
    private String adminToken = "";
}
