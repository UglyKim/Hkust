package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DoorTypeEnum {

    PROBE("探头");

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
