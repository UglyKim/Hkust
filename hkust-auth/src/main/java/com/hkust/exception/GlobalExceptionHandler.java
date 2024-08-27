package com.hkust.exception;

import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> HandleAccessDeniedException(AccessDeniedException ex) {

        return new ResponseEntity<>(ApiResponse.failed("用户无权限"), HttpStatus.UNAUTHORIZED);
//        return ResponseEntity.ok(ApiResponse.failed("此用户无权限访问"));
    }

    public ResponseEntity<?> handleValidationExceptions(UsernameNotFoundException ex) {
        return ResponseEntity.ok(ApiResponse.failed(ReturnCode.USER_IS_NULL));
    }

//    @ExceptionHandler(SQLException.class)
//    public ResponseEntity<?> handleValidationExceptions(SQLException ex) {
//        return ResponseEntity.ok(ApiResponse.failed(ReturnCode.DB_ERROR));
//    }
//
////    @ExceptionHandler(InvalidDefinitionException.class)
//    public ResponseEntity<?> handleValidationExceptions(InvalidDefinitionException ex) {
//        return ResponseEntity.ok(ApiResponse.failed(ReturnCode.SYSTEM_ERROR));
//    }

}