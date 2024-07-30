package com.hkust.dto.ao;

import com.hkust.enums.DoorTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class CabinetDoorAO implements Serializable {

    private static final long serialVersionUID = 8926468583003330109L;

    @Schema(required = true, description = "智能柜ID")
    private String cabinetId;

    @Schema(description = "柜门ID")
    private String doorId;

    @Schema(required = true, description = "柜门类型")
    private String type;

    @Schema(description = "名称")
    private String name;

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
