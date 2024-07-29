package com.hkust.exception;

import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public ResponseEntity<ApiResponse> handleClientError(InternalAuthenticationServiceException ex) {
        return new ResponseEntity<>(ApiResponse.failed(ReturnCode.AUTH_FAILED), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({InsufficientAuthenticationException.class})
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<ApiResponse> handleClientError1(NullPointerException ex) {
        return new ResponseEntity<>(ApiResponse.failed(ReturnCode.AUTH_FAILED), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>("Invalid input: " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleValidationExceptions(UsernameNotFoundException ex) {
        return ResponseEntity.ok(ApiResponse.failed(ReturnCode.USER_IS_NULL));
    }


}