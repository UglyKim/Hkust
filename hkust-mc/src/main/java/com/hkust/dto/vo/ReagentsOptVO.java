package com.hkust.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReagentsOptVO implements Serializable {


    private static final long serialVersionUID = 976811098787630109L;

    @Schema(required = true, description = "智能柜ID")
    private String cabinetId;

    @Schema(required = true, description = "柜门ID")
    private String doorId;

    @Schema(required = true, description = "试剂名称")
    private String reagentsName;

    @Schema(required = true, description = "操作人姓名")
    private String optUserName;

    @Schema(required = true, description = "操作时间")
    private LocalDateTime optDate;

    @Schema(required = true, description = "试剂操作类型")
    private String reagentsOptType;
}
