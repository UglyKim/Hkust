package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OptTypeEnum {

    INBOUND("1", "入库"),
    OUTBOUND("2", "出库"),
    OPEN("3", "开柜门"),
    CLOSE("4", "关柜门"),
    LOGIN("5", "登陆"),
    LOGOUT("6", "登出");

    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String fromCode(String code) {
        for (OptTypeEnum optTypeEnum : OptTypeEnum.values()) {
            if (optTypeEnum.getCode().equals(code)) {
                return optTypeEnum.getName();
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }
}
