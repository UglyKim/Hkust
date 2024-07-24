package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OpenMode {

    Single("1", "单人模式"),
    Double("2", "双人模式"),
    Other("3", "一管一用式");

    private String code;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static String fromCode(String code) {
        for (OpenMode openMode : OpenMode.values()) {
            if (openMode.getCode().equals(code)) {
                return openMode.getDescription();
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }
}
