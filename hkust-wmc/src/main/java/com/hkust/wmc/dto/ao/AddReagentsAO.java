package com.hkust.wmc.dto.ao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Schema(description = "试剂")
@AllArgsConstructor
@NoArgsConstructor
public class AddReagentsAO implements Serializable {

    private static final long serialVersionUID = 892198765676545679L;

    @Schema(description = "试剂编号")
    @NotNull(message = "请输入试剂编号")
    private String reagentsId;

    @Schema(description = "智能柜ID")
    @NotNull(message = "请输入智能柜编号")
    private String cabinetId;

    @Schema(description = "casNo")
    private String casNo;

    @Schema(description = "化学名称")
    @NotNull(message = "请输入试剂名称")
    private String name;

    @Schema(description = "英文名称")
    private String enName;

    @Schema(description = "类型")
    @Size(min = 1, max = 1, message = "长度为 1")
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
    @Size(min = 1, max = 1, message = "长度为 1")
    private String physicalState;

    @Schema(description = "特殊存储条件")
    private String specialStorageConditions;

    @Schema(description = "危险和危害特性类别")
    @Size(min = 1, max = 1, message = "长度为 1")
    private String hazardClassification;

    @Schema(description = "到期日")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @NotNull(message = "请输入有效期")
    private LocalDate expirationDate;

}
