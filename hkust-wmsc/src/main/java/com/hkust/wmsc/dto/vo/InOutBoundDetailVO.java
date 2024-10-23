package com.hkust.wmsc.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InOutBoundDetailVO implements Serializable {

    private static final long serialVersionUID = 9087666550988930109L;

    @Schema(description = "出入库时间")
    private String optTime;

    @Schema(description = "试剂编号")
    @NotNull
    private String reagentsId;

    @Schema(description = "casNo")
    private String casNo;

    @Schema(description = "化学名称")
    @NotNull
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
    private String ghs;

    @Schema(description = "课题组")
    private String researchGroup;

    @Schema(description = "状态")
    private String state;

    @Schema(description = "规格")
    private String specification;

    @Schema(description = "物理状态")
    private String physicalState;

    @Schema(description = "特殊存储条件")
    private String specialStorageConditions;

    @Schema(description = "危险和危害特性类别")
    private String hazardClassification;
}
