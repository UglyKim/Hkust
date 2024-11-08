package com.hkust.wmsc.dto.vo;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonPropertyOrder({"total", "inThisMonthTotal", "outThisMonthTotal"})
public class MainPageStaticsVO implements Serializable {

    private static final long serialVersionUID = 8926400000988930109L;

    @Schema(description = "总数")
    private int total;

    @Schema(description = "本月入库")
    private int inThisMonthTotal;

    @Schema(description = "本月出库")
    private int outThisMonthTotal;
}
