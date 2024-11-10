package com.hkust.wmsc.dto.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(description = "盘点结果上报")
@AllArgsConstructor
@NoArgsConstructor
public class StocktakingRecordResultAO implements Serializable {

    private static final long serialVersionUID = 998877115912009989L;

    @Schema(description = "化学试剂名称")
    @NotNull(message = "请填写试剂名称")
    private String name;

    @Schema(description = "规格")
    @NotNull(message = "请填写规格")
    private String specification;

    @Schema(description = "数量")
    @NotNull(message = "请填写数量")
    private String totalCount;
}
