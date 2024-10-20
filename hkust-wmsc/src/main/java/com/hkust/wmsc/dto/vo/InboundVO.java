package com.hkust.wmsc.dto.vo;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIncludeProperties({"inTime", "name", "count", "GHS"})
public class InboundVO implements Serializable {

    private static final long serialVersionUID = 9087622000988930109L;

    @Schema(required = true, description = "入库日期")
    private LocalDateTime inTime;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "数量")
    private String count;

    @Schema(description = "性质")
    private String GHS;
}
