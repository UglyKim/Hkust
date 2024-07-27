package com.hkust.dto.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReagentsVO {

    private static final long serialVersionUID = 3450876583003330109L;

    @Schema(required = true, description = "试剂ID")
    private String reagentsId;

    @Schema(required = true, description = "条形码")
    private String barcode;

    @Schema(required = true, description = "智能柜ID")
    private String cabinetNo;

    @Schema(required = true, description = "试剂名称")
    private String name;

    @Schema(required = true, description = "数量")
    private String qty;

    @Schema(required = true, description = "瓶身重量")
    private String bottleWeight;

    @Schema(required = true, description = "试剂重量")
    private String reagentWeight;

    @Schema(required = true, description = "到期日期")
    private LocalDate expireDate;

    @Schema(required = true, description = "是否过期")
    private String isExpire;

    @Schema(required = true, description = "操作人")
    private String operator;

    @Schema(required = true, description = "添加日期")
    private LocalDate addDate;

}
