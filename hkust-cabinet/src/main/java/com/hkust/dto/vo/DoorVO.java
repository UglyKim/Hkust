package com.hkust.dto.vo;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.hkust.enums.DoorType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@JsonIncludeProperties({"cabinetId", "cabinetName", "location", "cabinetAddr"})
public class DoorVO {

    @Schema(required = true, description = "柜门ID")
    private String doorId;

    @Schema(required = true, description = "智能柜ID")
    private String cabinetId;

    @Schema(required = true, description = "柜门名称")
    private String name;

    @Schema(required = true, description = "柜门类型")
    private String doorType;

    @Schema(required = true, description = "柜门位置")
    private String location;

    @Schema(required = true, description = "所属房间")
    private String room;

    @Schema(required = true, description = "柜门状态")
    private String stat;

    @Schema(required = true, description = "物品总量")
    private String reagentsQty;

    @Schema(required = true, description = "在柜数量")
    private String inQty;

    @Schema(required = true, description = "离柜数量")
    private String outQty;
}
