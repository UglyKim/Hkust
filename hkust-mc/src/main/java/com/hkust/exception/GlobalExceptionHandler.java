package com.hkust.exception;

import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
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