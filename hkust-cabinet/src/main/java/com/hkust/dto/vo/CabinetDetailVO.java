package com.hkust.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "智能柜详细信息，包括柜门详细信息和试剂统计信息")
public class CabinetDetailVO implements Serializable {

    private static final long serialVersionUID = 3456098790876546809L;

    @Schema(required = true, description = "智能柜ID")
    private String cabinetId;

    @Schema(required = true, description = "柜子名称")
    private String name;

    @Schema(required = true, description = "地址")
    private String location;

    @Schema(required = true, description = "柜子所在地址")
    private String cabinetAddr;

    @Schema(required = true, description = "柜门开启模式")
    private String openMode;

    @Schema(required = true, description = "试剂总数量")
    private int reagentCnt;

    @Schema(required = true, description = "试剂在柜数量")
    private int inReagentCnt;

    @Schema(required = true, description = "试剂离柜数量")
    private int outReagentCnt;

    @Schema(required = true, description = "柜门详细信息")
    List<CabinetDoorDetailVO> cabinetDoorDetailVOList;

    @Data
    public static class CabinetDoorDetailVO {

        @Schema(required = true, description = "智能柜门ID")
        private String cabinetDoorId;

        @Schema(required = true, description = "柜门类型")
        private String type;

        @Schema(description = "名称")
        private String name;

        @Schema(description = "房间")
        private String room;

        @Schema(description = "状态")
        private String stat;

        @Schema(description = "地址")
        private String location;

        @Schema(required = true, description = "试剂总数量")
        private int doorReagentCnt;

        @Schema(required = true, description = "试剂在柜数量")
        private int inDoorReagentCnt;

        @Schema(required = true, description = "试剂离柜数量")
        private int outDoorReagentCnt;


    }
}
