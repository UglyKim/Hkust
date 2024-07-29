package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EventTypeEnum {

    OPEN("1", "开柜门"),
    CLOSE("2", "关柜门"),
    TAKE("3", "取试剂"),
    RETURN("4", "归还试剂");

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
        for (EventTypeEnum eventTypeEnum : EventTypeEnum.values()) {
            if (eventTypeEnum.getCode().equals(code)) {
                return eventTypeEnum.getName();
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }
}
