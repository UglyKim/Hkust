package com.hkust.dto.ao.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ReagentsQueryAO {

    private static final long serialVersionUID = 8926400009876330109L;

    @Schema(required = true, description = "智能柜ID")
    @NotNull
    private String cabinetId;

    @Schema(required = true, description = "智能柜柜门ID")
    @NotNull
    private String doorId;

    @Schema(description = "试剂名称")
    private String reagentsName;

    @Schema(description = "到期天数")
    private String dueDays;

    @Schema(description = "剩余百分比")
    private String remainingPercent;

}
