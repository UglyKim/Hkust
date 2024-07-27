package com.hkust.constant;

import lombok.Getter;

@Getter
public enum ReturnCode {

    SUCCESS("00", "成功"),
    BAD_CREDENTIAL("98", "用户名密码错误"),
    FILE_IS_EMPTY("11", "请选择正确的操作录像"),
    FILE_NOT_MP4("12", "录像格式不正确"),
    FILE_UPLOAD_FAILED("13", "文件上传失败"),
    NOT_NULL("14", "空"),
    AUTH_FAILED("99", "令牌缺失或令牌格式不正确"),

    CABINET_ID_NOT_NULL("21", "请输入智能柜ID"),
    CABINET_IS_NULL("22", "智能柜不存在"),
    CABINET_DOOR_IS_NOT_NULL("23", "柜门不存在"),
    CABINET_DOOR_LIST_IS_NULL("23", "智能柜无柜门列表"),
    CABINET_DOOR_IS_NULL("24", "柜门不存在"),

    DB_UPDATE_ERROR("31", "更新数据失败"),
    DB_INSERT_ERROR("32", "新增数据失败");

    private final String code;

    private final String message;

    private ReturnCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
