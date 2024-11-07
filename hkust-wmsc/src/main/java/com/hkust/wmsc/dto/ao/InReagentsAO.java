package com.hkust.wmsc.dto.ao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Schema(description = "入库试剂信息")
public class InReagentsAO implements Serializable {

    private static final long serialVersionUID = 892955555998330109L;

    @Schema(description = "试剂ID， 读取二维码获取")
    @NotNull(message = "请输入试剂编号")
    private String reagentsId;

    @Schema(description = "设备号")
    @NotNull(message = "请输入设备编号")
    private String cabinetId;

    @Schema(description = "casNo")
    private String casNo;

    @Schema(description = "化学名称")
    @NotNull(message = "请输入试剂名称")
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
    @NotNull
    private String specification;

    @Schema(description = "物理状态")
    private String physicalState;

    @Schema(description = "特殊存储条件")
    private String specialStorageConditions;

    @Schema(description = "危险和危害特性类别")
    private String hazardClassification;

    @Schema(description = "到期日")
    @NotNull(message = "请输入到期日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate expiration_date;

    @Schema(description = "化学品危险属性")
    private String hazardProps;
}
