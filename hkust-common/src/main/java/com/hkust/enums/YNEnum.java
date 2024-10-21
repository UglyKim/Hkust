package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum YNEnum {

    YES("1", "是"),
    NO("0", "否");

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
        for (YNEnum ynEnum : YNEnum.values()) {
            if (ynEnum.getCode().equals(code)) {
                return ynEnum.getName();
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }

}
