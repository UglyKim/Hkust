package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CabinetStateEnum {

    ACTIVE("1", "正常"),
    STANDBY("2", "待机"),
    ERROR("3", "故障"),
    MAINTENANCE("4", "维护");

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
        for (CabinetStateEnum cabinetStateEnum : CabinetStateEnum.values()) {
            if (cabinetStateEnum.getCode().equals(code)) {
                return cabinetStateEnum.getName();
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }
}
