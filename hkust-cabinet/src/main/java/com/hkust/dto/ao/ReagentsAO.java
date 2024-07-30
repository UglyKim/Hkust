package com.hkust.dto.ao;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
public class ReagentsAO implements Serializable {

    private static final long serialVersionUID = 983368583005150707L;


    @Schema(required = true, description = "智能柜ID")
    @NotNull
    private String cabinetId;

    @Schema(required = true, description = "柜门ID")
    @NotNull
    private String doorId;

    @Schema(required = true, description = "试剂名称")
    @NotNull
    private String name;

    @Schema(required = true, description = "数量(瓶)")
    @NotNull
    private String qty;

    @Schema(required = true, description = "瓶身重量")
    private String bottleWeight;

    @Schema(required = true, description = "试剂重量")
    private String reagentWeight;

    @Schema(required = true, description = "到期日")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate expireDate;

    @Schema(description = "备注")
    private String remark;

    @Schema(required = true, description = "条形码")
    private String barCode;

}
