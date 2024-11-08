package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PhysicalStateEnum {

    SOLID("1", "固态"),
    LIQUID("2", "液态"),
    GAS("3", "气态"),
    SEMI_SOLID("4", "半固态"),
    PLASMA("5", "等离子态");

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
        for (PhysicalStateEnum physicalStateEnum : PhysicalStateEnum.values()) {
            if (physicalStateEnum.getCode().equals(code)) {
                return physicalStateEnum.getName();
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }
}
