package com.hkust.wmsc.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StocktakingVO implements Serializable {

    private static final long serialVersionUID = 9087666666666550909L;

    @Schema(description = "试剂名称")
    private String name;

    @Schema(description = "规格")
    private String specification;

    @Schema(description = "数量")
    private String totalCount;
}
