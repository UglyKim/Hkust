package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReagentsOptTypeEnum {

    TAKE("1", "取"),
    RETURN("2", "还");

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
}
