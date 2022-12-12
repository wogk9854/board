package com.sparta.board.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.board.dto.SecurityExceptionDto;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//security에 jwt인증 방법적용
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    //request헤더 부분에서 토큰을 가지고 온다
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //토큰을 가져온다
        String token = jwtUtil.resolveToken(request);

        if(token != null) {
            if(!jwtUtil.validateToken(token)){ //유효성검사
                jwtExceptionHandler(response, "Token Error", HttpStatus.UNAUTHORIZED.value());
                return; //false일 경우
            }
            Claims info = jwtUtil.getUserInfoFromToken(token); //토큰에서 유저정보 가져오기
            setAuthentication(info.getSubject()); //정보의 subjet안에 들어있는(username)
        }
        filterChain.doFilter(request,response);
        //토큰이 없고 인증이 필요없는 uri 같은경우는
    }
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}