package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnableEnum {

    YES("0", "是"),
    NO("1", "否");

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
        for (EnableEnum enableEnum : EnableEnum.values()) {
            if (enableEnum.getCode().equals(code)) {
                return enableEnum.getName();
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }
}
