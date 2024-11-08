package com.hkust.wmsc.dto.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(description = "出库试剂信息")
@AllArgsConstructor
public class OutReagentsAO implements Serializable {

    private static final long serialVersionUID = 892955555990000989L;

    @Schema(description = "试剂编号")
    @NotNull(message = "请输入试剂编号")
    private String reagentsId;

    @Schema(description = "casNo")
    private String casNo;

    @Schema(description = "化学名称")
    @NotNull(message = "请输入试剂信息")
    private String name;

    @Schema(description = "英文名称")
    private String enName;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "二维码")
    private String barcode;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "价格")
    private String price;

    @Schema(description = "存放位置")
    private String storageLocation;

    @Schema(description = "GHS")
    @NotNull
    private String ghs;

    @Schema(description = "课题组")
    private String researchGroup;

    @Schema(description = "状态")
    private String state;

    @Schema(description = "规格")
    @NotNull(message = "请输入规格")
    private String specification;

    @Schema(description = "物理状态")
    private String physicalState;

    @Schema(description = "特殊存储条件")
    private String specialStorageConditions;

    @Schema(description = "危险和危害特性类别")
    private String hazardClassification;
}
