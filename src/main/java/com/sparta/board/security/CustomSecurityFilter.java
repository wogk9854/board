package com.sparta.board.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.board.security.UserDetailsServiceImpl;
import com.sparta.board.dto.SecurityExceptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RequiredArgsConstructor
public class CustomSecurityFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("request.getRequestURI() = " + request.getRequestURI());


        if(username != null && password  != null && (request.getRequestURI().equals("/api/user/login") || request.getRequestURI().equals("/api/test-secured"))){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 비밀번호 확인
            if(!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new IllegalAccessError("비밀번호가 일치하지 않습니다.");
            }

            // 인증 객체 생성 및 등록
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            context.setAuthentication(authentication);

            SecurityContextHolder.setContext(context);
        }

        filterChain.doFilter(request,response);
    }

    @Component
    public static class CustomAccessDeniedHandler implements AccessDeniedHandler {
        private static final SecurityExceptionDto exceptionDto =
                new SecurityExceptionDto(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response,
                           AccessDeniedException accessDeniedException) throws IOException{

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.FORBIDDEN.value());

            try (OutputStream os = response.getOutputStream()) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(os, exceptionDto);
                os.flush();
            }
        }
    }

    @Component
    public static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
        private static final SecurityExceptionDto exceptionDto =
                new SecurityExceptionDto(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());

        @Override
        public void commence(HttpServletRequest request,
                             HttpServletResponse response,
                             AuthenticationException authenticationException) throws IOException {

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            try (OutputStream os = response.getOutputStream()) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(os, exceptionDto);
                os.flush();
            }
        }
    }
}