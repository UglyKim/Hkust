package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatEnum {

    NORMAL("1", "正常"),
    FAULT("2", "故障");

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
        for (StatEnum statEnum : StatEnum.values()) {
            if (statEnum.getCode().equals(code)) {
                return statEnum.getName();
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }
}
