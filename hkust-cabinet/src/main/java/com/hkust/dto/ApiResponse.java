package com.hkust.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hkust.constant.ReturnCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "description", "data"})
public class ApiResponse<T> implements Serializable {

    @Schema(description = "响应码 00:成功，其他:失败", required = true)
    private String code;

    @Schema
    private String message;

    @Schema
    private T data;

    @Schema
    private String description;

    /**
     * 成功
     *
     * @param <T>
     * @return
     */
    public static <T> ApiResponse success() {
        return new ApiResponse<T>(ReturnCode.SUCCESS.getCode(), ReturnCode.SUCCESS.getMessage(), null, null);
    }

    public static <T> ApiResponse success(T data) {
        return new ApiResponse<T>(ReturnCode.SUCCESS.getCode(), ReturnCode.SUCCESS.getMessage(), data, null);
    }

    public static <T> ApiResponse failed(ReturnCode returnCode) {
        return new ApiResponse<T>(returnCode.getCode(), returnCode.getMessage(), null, null);
    }

    public static <T> ApiResponse failed(String msg) {
        return new ApiResponse<T>(ReturnCode.SUCCESS.getCode(), msg, null, null);
    }

    @Override
    public String toString() {
        return "ResultUtils [code=" + code + ", message=" + message + ", data=" + data + "]";
    }

}
