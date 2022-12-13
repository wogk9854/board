//package com.sparta.board.Exception;
//
//import org.apache.catalina.connector.Response;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class CustomExceptionHandler {
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)//MethodArgumentNotValidException: @Valid 애너테이션으로 데이터를 검증하고, 해당 데이터에 에러가 있을 경우 예외 메세지를 JSON으로 처리하는 ExceptionHandler 처리 방법입니다.
//    public Response<?> handleValidationExceptions(MethodArgumentNotValidException exception) {
//        String errorMessage = exception.getBindingResult()
//                .getAllErrors()
//                .get(0)
//                .getDefaultMessage();
//
//        return ReponseDto.fail("BAD_REQUEST", errorMessage);
//    }
//}
