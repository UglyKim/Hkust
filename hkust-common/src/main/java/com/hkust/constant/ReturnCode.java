package com.hkust.constant;

import lombok.Getter;

@Getter
public enum ReturnCode {

    SUCCESS("00", "成功"),
    USER_IS_DISABLE("99", "黑名单用户"),
    USER_IS_NULL("97", "用户不存在"),
    PASSWD_MISMATCH("96", "密码不正确"),
    BAD_CREDENTIAL("98", "用户名密码错误"),
    USER_ALREADY_EXISTS("95", "用户已存在"),

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
    DB_INSERT_ERROR("32", "新增数据失败"),
    DB_ERROR("33", "数据库操作失败"),
    SYSTEM_ERROR("100", "系统错误!"),

    REAGENTS_EXPIRED("50", "试剂已到期"),
    REAGENTS_DELETE_FAILED("51", "试剂删除失败"),
    REAGENTS_IS_NULL("52", "试剂不存在"),
    REAGENTS_OUT("53", "试剂不在柜"),
    REAGENTS_IN("56", "试剂已在柜"),
    REAGENTS_RECORD_FAILED("54", "试剂操作记录插入失败"),
    REAGENTS_DOOR_MISMATCH("55", "请归还至指定柜子"),

    FILE_IS_NULL("61", "空录像");


    private final String code;

    private final String message;

    private ReturnCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
