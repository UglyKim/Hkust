package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum HazardPropsEnum {

    FLAMMABLE("1", "易燃"),
    EXPLOSIVE("2", "易爆");

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
        for (HazardPropsEnum hazardPropsEnum : HazardPropsEnum.values()) {
            if (hazardPropsEnum.getCode().equals(code)) {
                return hazardPropsEnum.getName();
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }
}
