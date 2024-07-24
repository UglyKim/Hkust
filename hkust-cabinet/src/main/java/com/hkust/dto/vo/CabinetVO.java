package com.hkust.dto.vo;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.hkust.dto.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonIncludeProperties({"cabinetId", "cabinetName", "location", "cabinetAddr"})
public class CabinetVO implements Serializable {

    @Schema(required = true, description = "智能柜编号")
    private String cabinetId;

    @Schema(required = true, description = "柜子名称")
    private String name;

    @Schema(required = true, description = "地址")
    private String location;

    @Schema(required = true, description = "柜子所在地址")
    private String cabinetAddr;

    @Schema(required = true, description = "柜门开启模式")
    private String openMode;

}
