package com.hkust.wmsc.dto.ao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Schema(description = "查询试剂信息表单内容- 统计")
@AllArgsConstructor
@NoArgsConstructor
public class ReagentsQueryAO implements Serializable {

    private static final long serialVersionUID = 987755555990009989L;

    @Schema(description = "页码")
    @Min(value = 1, message = "页码必须大于0")
    @NotNull(message = "请输入页码")
    private int pageNum;

    @Schema(description = "每页显示条数")
    @Min(value = 1, message = "每页显示条数必须大于0")
    private int pageSize;

    @Schema(description = "化学试剂名称")
    private String name;

    @Schema(description = "查询起始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startDate;

    @Schema(description = "查询结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endDate;

    @Schema(description = "是否在库 in:1 out:2")
    @NotNull(message = "请输入是否在库类型")
    private String inOut;

}
