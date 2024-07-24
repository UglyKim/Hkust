package com.hkust.constant;

public enum OpenMode {

    Single("单人模式"),
    Double("双人模式"),
    Other("一管一用式");

    OpenMode(String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
