package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReagentsOptType {

    GET("取"),
    RETURN("还");

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
