package com.hkust.constant;

import lombok.Getter;

@Getter
public enum ReturnCode {

    SUCCESS("00", "成功"),
    BAD_CREDENTIAL("", "用户名密码错误"),
    FILE_IS_EMPTY("11", "请选择正确的操作录像"),
    FILE_NOT_MP4("12", "录像格式不正确"),
    FILE_UPLOAD_FAILED("13", "文件上传失败"),
    AUTH_FAILED("99","认证失败，请重新登陆!");


    private final String code;

    private final String message;

    private ReturnCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
