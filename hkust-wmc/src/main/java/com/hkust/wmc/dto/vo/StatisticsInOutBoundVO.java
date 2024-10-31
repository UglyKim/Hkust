package com.hkust.wmc.dto.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(description = "首页-月度出入库统计")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsInOutBoundVO implements Serializable {

    private static final long serialVersionUID = 892999665544326543L;

    @Schema(description = "年月")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String month;

    @Schema(description = "入库统计")
    private int inboundCount;

    @Schema(description = "出库统计")
    private int outboundCount;
}
