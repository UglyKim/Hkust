package com.hkust.wmc.dto.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Schema(description = "仓储智能柜")
public class CabinetAO implements Serializable {

    private static final long serialVersionUID = 892099988998330109L;

    @Schema(description = "安全柜类型, 具体类型暂时不知道是多少，先填写1个长度的字符串")
    @Size(min = 1, max = 1,message = "长度为 1")
    private String type;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "二维码")
    private String barcode;

    @Schema(description = "存放房间")
    private String storageRoom;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "规格")
    private String specification;

    @Schema(description = "层数")
    private  int layerCount;

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
}
