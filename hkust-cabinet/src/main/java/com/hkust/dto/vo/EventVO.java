package com.hkust.dto.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hkust.dto.PageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EventVO {

    @Schema(required = true, description = "学员号")
    private String userName;

    @Schema(required = true, description = "学员姓名")
    private String realName;

    @Schema(required = true, description = "操作ID")
    private String eventId;

    @Schema(required = true, description = "智能柜ID")
    private String cabinetId;

    @Schema(required = true, description = "操作类型")
    private String eventType;

    @Schema(required = true, description = "描述")
    private String content;

    @TableField("opt_date")
    private LocalDate optDate;
}
