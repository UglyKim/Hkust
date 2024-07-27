package com.hkust.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CabinetDoorVO {

    private static final long serialVersionUID = 3456468583003330109L;

    @Schema(required = true, description = "智能柜ID")
    private String cabinetId;

    @Schema(required = true, description = "柜门ID")
    private String cabinetDoorId;

    @Schema(required = true, description = "柜门类型")
    private String type;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "房间")
    private String room;

    @Schema(description = "状态")
    private String stat;

    @Schema(required = true, description = "在柜总数量")
    private int reagentsQty;

    @Schema(required = true, description = "在柜数量")
    private int inQty;

    @Schema(required = true, description = "离柜数量")
    private int outQty;

    @Schema(description = "地址")
    private String location;
}
