package com.hkust.exception;

public class JwtValidationException extends RuntimeException {

    private final String errorCode;

    public JwtValidationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
