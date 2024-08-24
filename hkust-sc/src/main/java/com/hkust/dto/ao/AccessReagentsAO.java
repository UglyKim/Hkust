package com.hkust.dto.ao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
public class AccessReagentsAO implements Serializable {

    private static final long serialVersionUID = 8926400009998330109L;

    @Schema(description = "页码")
    @NotNull
    private int pageNum;

    @Schema(description = "显示条数")
    @NotNull
    private int pageSize;

    @Schema(description = "智能柜ID")
    private String cabinetId;

    @Schema(description = "柜门ID")
    @NotNull
    private String doorId;
}
