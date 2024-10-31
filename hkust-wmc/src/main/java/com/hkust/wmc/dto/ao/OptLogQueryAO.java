package com.hkust.wmc.dto.ao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Schema(description = "日志查询")
public class OptLogQueryAO implements Serializable {

    private static final long serialVersionUID = 899000087700005679L;

    @Schema(description = "页码")
    @NotNull
    private int pageNum;

    @Schema(description = "显示条数")
    @NotNull
    private int pageSize;

    @Schema(description = "操作类型, 1:操作日志 2:试剂日志")
    @NotNull
    @Size(min = 1, max = 1)
    private String type;

    @Schema(description = "操作人")
    private String operator;

    @Schema(description = "查询起始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startDate;

    @Schema(description = "查询结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endDate;
}
