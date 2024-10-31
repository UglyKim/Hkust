package com.hkust.wmc.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Schema(description = "试剂列表-顶部统计")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReagentsStatisticsVO implements Serializable {

    private static final long serialVersionUID = 892999665123455556L;

    @Schema(description = "当前总库")
    private int count;

    @Schema(description = "本月入库")
    private int inThisMonthTotal;

    @Schema(description = "本月出库")
    private int outThisMonthTotal;

    @Schema(description = "最小库存")
    private int minInventory;

    @Schema(description = "最大库存")
    private int maxInventory;

}
