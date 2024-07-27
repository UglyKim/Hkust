package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OpenModeEnum {

    Single("1", "单人模式"),
    Double("2", "双人模式"),
    Other("3", "一管一用式");

    private String code;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static String fromCode(String code) {
        for (OpenModeEnum openModeEnum : OpenModeEnum.values()) {
            if (openModeEnum.getCode().equals(code)) {
                return openModeEnum.getDesc();
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }

    public static String fromDesc(String desc) {
        for (OpenModeEnum openModeEnum : OpenModeEnum.values()) {
            if (openModeEnum.name().equals(desc)) {
                return openModeEnum.getCode();
            }
        }
        throw new IllegalArgumentException("No enum constant with desc " + desc);
    }
}
