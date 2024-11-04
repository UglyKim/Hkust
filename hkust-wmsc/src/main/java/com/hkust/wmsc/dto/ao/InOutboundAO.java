package com.hkust.wmsc.dto.ao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Schema(description = "出入库统计和查询")
public class InOutboundAO implements Serializable {

    private static final long serialVersionUID = 8929099809998330109L;

    @Schema(description = "页码")
    @NotNull(message = "请输入页码")
    private int pageNum;

    @Schema(description = "显示条数")
    @NotNull(message = "请输入每页显示条数")
    private int pageSize;

    @Schema(description = "查询起始日期")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startDate;

    @Schema(description = "查询结束日期")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endDate;

    @Schema(description = "化学名称")
    private String name;

    @Schema(description = "出入库 in:入库 out:出库")
    @NotNull
    private String type;
}
