package com.hkust.dto.ao;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"cabinetId", "location", "cabinetAddr"})
public class CabinetAO {

    private static final long serialVersionUID = 5926468583003330707L;

    @Schema(required = true, description = "智能柜ID")
    private String cabinetId;

    @Schema(required = true, description = "智能柜名称")
    private String name;

    @Schema(required = true, description = "位置")
    private String location;

    @Schema(required = true, description = "智能柜mac地址")
    private String mac;

    @Schema(required = true, description = "智能柜所在地址：鼓楼广场1号仓库")
    private String cabinetAddr;

    @Schema(required = true, description = "智能柜状态")
    private String stat;

    @Schema(required = true, description = "柜门开启模式")
    private String openMode;

    @Schema(description = "备注")
    private String remark;
}
