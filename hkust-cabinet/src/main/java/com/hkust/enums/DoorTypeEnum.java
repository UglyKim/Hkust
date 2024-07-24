package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DoorType {

    PROBE("探头");

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
