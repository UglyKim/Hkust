package com.hkust.wmsc.dto.ao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Schema(description = "查询试剂信息表单内容- 统计")
public class ReagentsQueryAO implements Serializable {

    private static final long serialVersionUID = 987755555990009989L;

    @Schema(description = "页码")
    @NotNull
    private int pageNum;

    @Schema(description = "显示条数")
    @NotNull
    private int pageSize;

    @Schema(description = "化学试剂名称")
    private String name;

    @Schema(description = "查询起始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startDate;

    @Schema(description = "查询结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endDate;

    @Schema(description = "出入库 in:1 out:2")
    private String type;

}
