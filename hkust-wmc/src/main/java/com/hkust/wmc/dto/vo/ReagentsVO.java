package com.hkust.wmc.dto.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Schema(description = "试剂")
@Data
@AllArgsConstructor
public class ReagentsVO implements Serializable {

    private static final long serialVersionUID = 892999665544326543L;

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

    @Schema(description = "到期日")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate expirationDate;

    @Schema(description = "是否过期")
    private Boolean isExp;
}
