package com.hkust.wmsc.dto.ao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Schema(description = "盘点")
public class StocktakingAO implements Serializable {

    private static final long serialVersionUID = 555533115990009989L;

    //    @Deprecated
    @Schema(description = "页码")
    @Min(value = 1, message = "页码必须大于0")
    private int pageNum;

    //    @Deprecated
    @Schema(description = "每页显示条数")
    @Min(value = 1, message = "每页显示条数必须大于0")
    private int pageSize;

    @Schema(description = "查询起始日期")
    @NotNull(message = "请输入查询起始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startDate;

    @Schema(description = "查询结束日期")
    @NotNull(message = "请输入查询结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endDate;
}
