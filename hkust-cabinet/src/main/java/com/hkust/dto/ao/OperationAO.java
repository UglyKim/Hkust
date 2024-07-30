package com.hkust.dto.ao;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonPropertyOrder({"cabinetId", "eventType"})
public class OperationAO implements Serializable {

    private static final long serialVersionUID = 983368583005150333L;

    @Schema(required = true, description = "操作类型")
    private String eventType;

    @Schema(required = true, description = "智能柜ID")
    private String cabinetId;

    @Schema(required = true, description = "智能柜柜门ID")
    private String doorId;

    @Schema(description = "描述")
    private String content;
}
