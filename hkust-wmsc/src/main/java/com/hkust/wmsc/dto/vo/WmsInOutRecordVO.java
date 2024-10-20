package com.hkust.wmsc.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "出入库记录")
public class WmsInOutRecordVO {

    private static final long serialVersionUID = 998877667990000989L;

    @Schema(description = "编号")
    private String id;

    @Schema(description = "出入库类型")
    private String type;

    @Schema(description = "试剂编号")
    private String reagentsId;

    @Schema(description = "")
    private String reagentsName;

    @Schema(description = "规格")
    private String specification;

    @Schema(description = "GHS")
    private String ghs;

    @Schema(description = "出入库时间")
    private LocalDateTime optTime;

    @Schema(description = "操作人")
    private String operator;
}
