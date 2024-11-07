package com.hkust.wmsc.dto.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Schema(description = "临期查询")
public class ExpReagentsQueryAO implements Serializable {

    private static final long serialVersionUID = 666676555990009989L;

    @Schema(description = "页码")
    @Min(value = 1, message = "页码必须大于0")
    private int pageNum;

    @Schema(description = "显示条数")
    @Min(value = 1, message = "每页显示条数必须大于0")
    private int pageSize;
}
