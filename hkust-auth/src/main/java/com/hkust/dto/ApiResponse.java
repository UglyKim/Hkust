package com.hkust.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hkust.constant.ReturnCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "data"})
public class ApiResponse<T> implements Serializable {

    @Schema(description = "响应码 00:成功，其他:失败", required = true)
    private String code;

    @Schema
    private String message;

    @Schema
    private T data;

    public ApiResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(ReturnCode.SUCCESS.getCode(), ReturnCode.SUCCESS.getMessage(), null);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(ReturnCode.SUCCESS.getCode(),message,null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ReturnCode.SUCCESS.getCode(), ReturnCode.SUCCESS.getMessage(), data);
    }

    public static <T> ApiResponse<T> failed(ReturnCode returnCode) {
        return new ApiResponse<>(returnCode.getCode(), returnCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> failed(String msg) {
        return new ApiResponse<>(ReturnCode.SUCCESS.getCode(), msg, null);
    }

    @Override
    public String toString() {
        return "ResultUtils [code=" + code + ", message=" + message + ", data=" + data + "]";
    }

}
