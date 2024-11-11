package com.hkust.wmc.dto.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(description = "仓储智能柜")
@Data
@AllArgsConstructor
public class CabinetVO implements Serializable {

    private static final long serialVersionUID = 892955555998876543L;

    @Schema(description = "仓储智能柜ID")
    private String cabinetId;

    @Schema(description = "使用状态")
    private String state;

    @Schema(description = "安全柜类型")
    private String type;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "二维码")
    private String barcode;

    /**
     * 审核状态
     */
    @Schema(description = "审核状态")
    private String review_state;

    @Schema(description = "存放房间")
    private String storageRoom;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "规格")
    private String specification;

    @Schema(description = "层数")
    private  int layerCount;

    @Schema(description = "创建人")
    private String creator;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @Schema(description = "审核人")
    private String auditor;

    @Schema(description = "审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime auditTime;

    @Schema(description = "材料")
    private String material;

    @Schema(description = "阈值比例")
    private  String thresholdRatio;

    @Schema(description = "楼栋")
    private String  teachingBuilding;

    @Schema(description = "容量")
    private String capacity;

    @Schema(description = "废液桶名称")
    private String wasteContainerName;

    @Schema(description = "盛庄废液类型")
    private String wasteLiquidsType;

    @Schema(description = "修改人")
    private LocalDateTime modifiedBy;

    @Schema(description = "修改时间")
    private LocalDateTime modifiedTime;
}
