package com.hkust.exception;

import com.hkust.constant.ReturnCode;
import com.hkust.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InternalAuthenticationServiceException.class})
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseStatus(HttpStatus.FOUND)
    public ApiResponse handleClientError(InternalAuthenticationServiceException ex) {
        return ApiResponse.failed(ReturnCode.BAD_CREDENTIAL);
    }

    @ExceptionHandler({NullPointerException.class})
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseStatus(HttpStatus.FOUND)
    public ApiResponse handleClientError1(NullPointerException ex) {
        return ApiResponse.failed(ReturnCode.BAD_CREDENTIAL);
    }
}