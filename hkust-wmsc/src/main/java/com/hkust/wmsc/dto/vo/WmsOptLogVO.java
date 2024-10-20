package com.hkust.wmsc.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "查询试剂列表")
public class WmsOptLogVO {

    private static final long serialVersionUID = 698789087990000989L;

    @Schema(description = "操作ID")
    private String id;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "操作人")
    private String operator;

    @Schema(description = "操作时间")
    private LocalDateTime opt_time;
}
