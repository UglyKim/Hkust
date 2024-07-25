package com.hkust.dto.ao;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@JsonPropertyOrder({"barcode", "cabinetId", "doorId", "name"})
public class ReagentsAO {

    @Schema(required = true, description = "条形码")
    private String barcode;

    @Schema(required = true, description = "智能柜ID")
    private String cabinetId;

    @Schema(required = true, description = "柜门ID")
    private String doorId;

    @Schema(required = true, description = "试剂名称")
    private String name;

    @Schema(required = true, description = "数量(瓶)")
    private String qty;

    @Schema(required = true, description = "瓶身重量")
    private String bottleWeight;

    @Schema(required = true, description = "试剂重量")
    private String reagentWeight;

    @Schema(required = true, description = "到期日")
    private LocalDate expireDate;

    @Schema(required = true, description = "憋住")
    private String remark;

}
