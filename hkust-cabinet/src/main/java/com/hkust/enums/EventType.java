package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EventType {

    OPEN("1", "开柜门"),
    CLOSE("2", "关柜门"),
    GET("3", "取试剂"),
    RETURN("4", "归还试剂");

    private String code;
    private String opt;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public static String fromCode(String code) {
        for (EventType eventType : EventType.values()) {
            if (eventType.getCode().equals(code)) {
                return eventType.getOpt();
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }
}
