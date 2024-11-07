package com.hkust.wmsc.dto.ao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Schema(description = "日志查询")
public class OptLogQueryAO implements Serializable {

    private static final long serialVersionUID = 892955550987630109L;

    @Schema(description = "页码")
    @Min(value = 1, message = "页码必须大于0")
    private int pageNum;

    @Schema(description = "显示条数")
    @Min(value = 1, message = "每页显示条数必须大于0")
    private int pageSize;

    @Schema(description = "操作类型 1:操作日志 2:试剂日志 3.全部")
    @NotNull
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
