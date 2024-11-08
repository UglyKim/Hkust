package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InOutEnumType {

    IN("1", "在库"),
    OUT("2", "离库");

    private String code;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static String fromCode(String code) {
        for (InOutEnumType inOutEnumType : InOutEnumType.values()) {
            if (inOutEnumType.getCode().equals(code)) {
                return inOutEnumType.getName();
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
