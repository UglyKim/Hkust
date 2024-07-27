package com.hkust.dto.ao;

import com.hkust.enums.DoorTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CabinetDoorAO {

    private static final long serialVersionUID = 8926468583003330109L;

    @Schema(required = true, description = "智能柜ID")
    private String cabinetId;

    @Schema(required = true, description = "柜门类型")
    private String type;

    @Schema(description = "房间")
    private String room;

    @Schema(description = "状态")
    private String stat;

    @Schema(description = "在柜总数量")
    private int reagentsQty;

    @Schema(description = "在柜数量")
    private int inQty;

    @Schema(description = "离柜数量")
    private int outQty;

    @Schema(description = "地址")
    private String location;
}
