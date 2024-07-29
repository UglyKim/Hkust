package com.hkust.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OptReagentsVO {

    private static final long serialVersionUID = 3450800000003909090L;

    @Schema(required = true, description = "姓名")
    private String realName;

    @Schema(required = true, description = "取试剂操作时间")
    private LocalDateTime takeTime;

    @Schema(required = true, description = "操作类型：存或者取")
    private String optType;
}
