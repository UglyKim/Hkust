package com.hkust.dto.ao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hkust.entity.Event;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@JsonPropertyOrder({"pageNum", "startDate", "endDate"})
public class EventQueryAO implements Serializable {

    private static final long serialVersionUID = 8233368583003330707L;

    @Schema(description = "页码")
    @NotNull
    private int pageNum;

    @Schema(description = "显示条数")
    @NotNull
    private int pageSize;

    @Schema(description = "查询起始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startDate;

    @Schema(description = "查询结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endDate;

}
