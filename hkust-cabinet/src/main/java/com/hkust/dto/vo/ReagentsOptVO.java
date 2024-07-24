package com.hkust.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ReagentsOptVO {

    @Schema(required = true, description = "柜门ID")
    private String doorId;

    @Schema(required = true, description = "试剂名称")
    private String reagentsName;

    @Schema(required = true, description = "操作人姓名")
    private String optUserName;

    @Schema(required = true, description = "操作时间")
    private Date optDate;

    @Schema(required = true, description = "试剂操作类型")
    private String reagentsOptType;
}
