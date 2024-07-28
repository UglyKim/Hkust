package com.hkust.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class TakeReagentsVO {

    private static final long serialVersionUID = 3450876583003909090L;

    @Schema(required = true, description = "姓名")
    private String realName;

    @Schema(required = true, description = "取试剂操作时间")
    private LocalDateTime takeTime;
}
