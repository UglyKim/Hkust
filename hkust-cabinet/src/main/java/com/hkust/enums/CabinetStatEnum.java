package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CabinetStatEnum {

    normal("1", "正常"),
    fault("2", "故障");

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
        for (CabinetStatEnum cabinetStatEnum : CabinetStatEnum.values()) {
            if (cabinetStatEnum.getCode().equals(code)) {
                return cabinetStatEnum.getName();
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }
}
