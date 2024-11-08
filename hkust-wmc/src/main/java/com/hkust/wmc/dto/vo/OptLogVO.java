package com.hkust.wmc.dto.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(description = "操作日志")
@Data
@AllArgsConstructor
public class OptLogVO implements Serializable {

    private static final long serialVersionUID = 892955555999900003L;

    @Schema(description = "日志ID")
    private String id;

    @Schema(description = "操作类型")
    private String type;

    @Schema(description = "操作人ID")
    private String operatorId;

    @Schema(description = "操作人")
    private String operator;

    @Schema(description = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime optTime;
}
