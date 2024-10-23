package com.hkust.wmsc.dto.ao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Schema(description = "盘点")
public class StocktakingAO implements Serializable {

    private static final long serialVersionUID = 555533115990009989L;

//    @Deprecated
    @Schema(description = "页码")
    @NotNull
    private int pageNum;

//    @Deprecated
    @Schema(description = "显示条数")
    @NotNull
    private int pageSize;

    @Schema(description = "查询起始日期")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startDate;

    @Schema(description = "查询结束日期")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endDate;
}
