package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SexEnum {
    M("男"),
    F("女");

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
