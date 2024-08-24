package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DoorTypeEnum {

    PROBE("1", "探头");

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
        for (DoorTypeEnum doorTypeEnum : DoorTypeEnum.values()) {
            if (doorTypeEnum.getCode().equals(code)) {
                return doorTypeEnum.getName();
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }
}
