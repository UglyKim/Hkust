package com.hkust.dto.ao;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hkust.entity.Event;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@JsonPropertyOrder({"pageNum", "startDate", "endDate"})
public class EventQueryAO {

    private static final long serialVersionUID = 8233368583003330707L;

    @Schema(description = "页码")
    private int pageNum;

    @Schema(description = "查询起始日期")
    private LocalDate startDate;

    @Schema(description = "查询结束日期")
    private LocalDate endDate;

}
