package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GenderEnum {
    M("男"),
    F("女");

    private String name;

    public String getName() {
        return name;
    }

//    public String getCode(GenderEnum e) {
//        return e.name();
//    }

    public void setName(String name) {
        this.name = name;
    }
}
