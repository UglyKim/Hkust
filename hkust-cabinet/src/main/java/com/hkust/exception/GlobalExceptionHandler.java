package com.hkust.exception;

import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public ResponseEntity<ApiResponse> handleClientError(InternalAuthenticationServiceException ex) {
        return new ResponseEntity<>(ApiResponse.failed(ReturnCode.BAD_CREDENTIAL), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NullPointerException.class})
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<ApiResponse> handleClientError1(NullPointerException ex) {
        return new ResponseEntity<>(ApiResponse.failed(ReturnCode.NOT_NULL), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}