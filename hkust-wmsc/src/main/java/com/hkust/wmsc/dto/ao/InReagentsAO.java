package com.hkust.wmsc.dto.ao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Schema(description = "入库试剂信息")
public class InReagentsAO implements Serializable {

    private static final long serialVersionUID = 892955555998330109L;

    @Schema(description = "设备号")
    @NotNull
    private String cabinetId;

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
    @NotNull
    private String ghs;

    @Schema(description = "课题组")
    private String researchGroup;

    @Schema(description = "状态")
    private String state;

    @Schema(description = "规格")
    @NotNull
    private String specification;

    @Schema(description = "物理状态")
    private String physicalState;

    @Schema(description = "特殊存储条件")
    private String specialStorageConditions;

    @Schema(description = "危险和危害特性类别")
    private String hazardClassification;

    @Schema(description = "到期日")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate expiration_date;

    @Schema(description = "化学品危险属性")
    private String hazardProps;
}
