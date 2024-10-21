package com.hkust.wmsc.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "出入库统计页面- 顶部的统计内容")
public class InOutBoundStatisticsVO implements Serializable {

    private static final long serialVersionUID = 9087666550000098109L;

    @Schema(description = "总数")
    private String totalCount;

    @Schema(description = "本月出库/入库总数")
    private String inOutCount;

}
