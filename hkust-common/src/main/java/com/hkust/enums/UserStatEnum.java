package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatEnum {

    ACTIVE("1", "活跃"),
    INACTIVE("2", "不活跃"),
    PENDING("3", "待处理"),
    LOCKED("4", "已锁定");

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
        for (UserStatEnum userStatEnum : UserStatEnum.values()) {
            if (userStatEnum.getCode().equals(code)) {
                return userStatEnum.getName();
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }
}
