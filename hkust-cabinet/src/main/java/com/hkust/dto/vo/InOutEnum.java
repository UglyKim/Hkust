package com.hkust.dto.vo;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum InOutEnum {

    IN("0", "在柜"),
    OUT("1", "离柜");

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
