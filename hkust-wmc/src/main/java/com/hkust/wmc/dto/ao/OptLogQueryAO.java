package com.hkust.wmc.dto.ao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Schema(description = "日志查询")
@AllArgsConstructor
@NoArgsConstructor
public class OptLogQueryAO implements Serializable {

    private static final long serialVersionUID = 899000087700005679L;

    @Schema(description = "页码")
    @Min(value = 1, message = "页码显示条数必须大于0")
    private int pageNum;

    @Schema(description = "显示条数")
    @Min(value = 1, message = "每页显示条数必须大于0")
    private int pageSize;

    @Schema(description = "操作类型, 1:操作日志 2:试剂日志", defaultValue = "1")
    @NotNull(message = "请输入类型")
    @Size(min = 1, max = 1,message = "长度为 1")
    private String type;

    @Schema(description = "操作人")
    private String operator;

    @Schema(description = "查询起始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startDate;

    @Schema(description = "查询结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endDate;
}
