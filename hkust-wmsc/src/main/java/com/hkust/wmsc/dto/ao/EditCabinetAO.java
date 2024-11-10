package com.hkust.wmsc.dto.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(description = "智能柜编辑")
@AllArgsConstructor
@NoArgsConstructor
public class EditCabinetAO implements Serializable {

    private static final long serialVersionUID = 888896533990009989L;

    @Schema(description = "设备ID")
    @NotNull(message = "请输入设备编号")
    private String cabinetId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "二维码")
    private String barcode;

    @Schema(description = "使用状态")
    private String state;

    @Schema(description = "存放房间")
    private String storageRoom;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "规格")
    private String specification;

    @Schema(description = "层数")
    private  int layerCount;

    @Schema(description = "阈值比例")
    private  String thresholdRatio;

    @Schema(description = "容量")
    private String capacity;

    @Schema(description = "备注")
    private String remark;
}
