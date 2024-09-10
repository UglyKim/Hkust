package com.hkust.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CabinetTypeEnum {

    FIREPROOF("1", "防火柜"),
    ACID_ALKALI("2", "酸碱柜"),
    WASTE_LIQUID("3", "废液柜"),
    DRYING("4", "干燥柜"),
    NITROGEN("5", "氮气柜");

    private String code;

    private String name;

    public static String fromCode(String code) {
        for (CabinetTypeEnum cabinetTypeEnum : CabinetTypeEnum.values()) {
            if (cabinetTypeEnum.getCode().equals(code)) {
                return cabinetTypeEnum.getName();
            }
        }
        throw new IllegalArgumentException("No enum constant with code " + code);
    }

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
