package com.hkust.wmsc.dto.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Schema(description = "临期查询")
public class ExpReagentsQueryAO implements Serializable {

    private static final long serialVersionUID = 666676555990009989L;

    @Schema(description = "页码")
    @NotNull
    private int pageNum;

    @Schema(description = "显示条数")
    @NotNull
    private int pageSize;
}
